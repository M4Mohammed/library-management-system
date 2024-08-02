package com.project.librarymanagementsystem.books;

import com.project.librarymanagementsystem.records.BorrowingRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    @Column(name = "date_added", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateAdded;

    private String title;

    private String ISBN;

    private String publisher;

    private String author;

    private String genre;

    private String language;

    @Column(name = "number_of_pages")
    private int numberOfPages;

    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecords;
}
