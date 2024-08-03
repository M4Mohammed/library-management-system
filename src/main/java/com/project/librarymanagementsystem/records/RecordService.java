package com.project.librarymanagementsystem.records;

import com.project.librarymanagementsystem.exceptions.BookNotFoundException;
import com.project.librarymanagementsystem.books.Book;
import com.project.librarymanagementsystem.books.BookRepository;
import com.project.librarymanagementsystem.patrons.Patron;
import com.project.librarymanagementsystem.patrons.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;


    @Transactional
    public Record borrowBook(UUID bookId, UUID patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new BookNotFoundException(patronId));

        if (recordRepository.existsByBookAndReturnDateIsNull(book)) {
            throw new IllegalStateException("Book is already borrowed");
        }

        Record borrowRecord = new Record();
        borrowRecord.setBook(book);
        borrowRecord.setPatron(patron);
        borrowRecord.setBorrowDate(LocalDateTime.now());

        return recordRepository.save(borrowRecord);
    }

    @Transactional
    public Record returnBook(UUID bookId, UUID patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new BookNotFoundException(patronId));

        Record returnRecord = recordRepository
                .findByBookAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new IllegalStateException("Book is not borrowed by this patron"));

        returnRecord.setReturnDate(LocalDateTime.now());

        return recordRepository.save(returnRecord);
    }
}
