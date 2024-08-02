package com.project.librarymanagementsystem.records;

import com.project.librarymanagementsystem.books.Book;
import com.project.librarymanagementsystem.patrons.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, UUID> {
    boolean existsByBookAndReturnDateIsNull(Book book);

    Optional<BorrowingRecord> findByBookAndReturnDateIsNull(Book book, Patron patron);
}