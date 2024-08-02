package com.project.librarymanagementsystem.records;

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
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;


    @Transactional
    public BorrowingRecord borrowBook(UUID bookId, UUID patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(/*() -> new ResourceNotFoundException("Book not found")*/);

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(/*() -> new ResourceNotFoundException("Patron not found")*/);

        if (borrowingRecordRepository.existsByBookAndReturnDateIsNull(book)) {
            throw new IllegalStateException("Book is already borrowed");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDateTime.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnBook(UUID bookId, UUID patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(/*() -> new ResourceNotFoundException("Book not found")*/);

        Patron patron = patronRepository.findById(bookId)
                .orElseThrow(/*() -> new ResourceNotFoundException("Patron not found")*/);

        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findByBookAndReturnDateIsNull(book, patron)
                .orElseThrow(/*() -> new ResourceNotFoundException("Borrowing record not found")*/);

        borrowingRecord.setReturnDate(LocalDateTime.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }
}
