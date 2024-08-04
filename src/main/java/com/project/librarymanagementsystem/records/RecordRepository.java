package com.project.librarymanagementsystem.records;

import com.project.librarymanagementsystem.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, RecordId> {
    boolean existsByBookAndReturnDateIsNull(Book book);

    Optional<Record> findByIdAndReturnDateIsNull(RecordId recordId);
}