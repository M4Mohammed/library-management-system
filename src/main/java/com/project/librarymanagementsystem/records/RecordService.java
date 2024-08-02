package com.project.librarymanagementsystem.records;

import com.project.librarymanagementsystem.exceptions.BookNotFoundException;
import com.project.librarymanagementsystem.books.Book;
import com.project.librarymanagementsystem.books.BookRepository;
import com.project.librarymanagementsystem.patrons.Patron;
import com.project.librarymanagementsystem.patrons.PatronRepository;
import lombok.RequiredArgsConstructor;
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

        Record record = new Record();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDateTime.now());

        return recordRepository.save(record);
    }

    @Transactional
    public Record returnBook(UUID bookId, UUID patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new BookNotFoundException(patronId));

        Record record = recordRepository
                .findByBookAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new IllegalStateException("Book is not borrowed by this patron"));

        record.setReturnDate(LocalDateTime.now());

        return recordRepository.save(record);
    }
}
