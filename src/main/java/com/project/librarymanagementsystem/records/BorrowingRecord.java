package com.project.librarymanagementsystem.records;

import com.project.librarymanagementsystem.books.Book;
import com.project.librarymanagementsystem.patrons.Patron;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @Temporal(TemporalType.DATE)
    @Column(name = "borrow_date", nullable = false)
    private Date borrowDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;
}