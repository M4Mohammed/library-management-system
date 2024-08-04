package com.project.librarymanagementsystem.records;

import com.project.librarymanagementsystem.exceptions.BookNotFoundException;
import com.project.librarymanagementsystem.books.Book;
import com.project.librarymanagementsystem.books.BookRepository;
import com.project.librarymanagementsystem.patrons.Patron;
import com.project.librarymanagementsystem.patrons.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(value = "bookCache", key = "#bookId")
    public Record borrowBook(UUID bookId, UUID patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new BookNotFoundException(patronId));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }

        if (recordRepository.existsByBookAndReturnDateIsNull(book)) {
            throw new IllegalStateException("Book is already borrowed");
        }

        RecordId recordId = new RecordId(bookId, patronId);

        Record borrowRecord = Record.builder()
                .id(recordId)
                .book(book)
                .patron(patron)
                .borrowDate(LocalDateTime.now())
                .build();

        return recordRepository.save(borrowRecord);
    }

    @Transactional
    @CacheEvict(value = "bookCache", key = "#bookId")
    public Record returnBook(UUID bookId, UUID patronId) {

        RecordId recordId = new RecordId(bookId, patronId);

        Record record = recordRepository
                .findByIdAndReturnDateIsNull(recordId)
                .orElseThrow(() -> new IllegalStateException("No active borrowing record found for this book and patron"));

        record.setReturnDate(LocalDateTime.now());

        Record updatedRecord = recordRepository.save(record);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        book.setAvailable(true);
        bookRepository.save(book);

        return updatedRecord;
    }
;}
