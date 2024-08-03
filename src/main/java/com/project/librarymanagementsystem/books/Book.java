package com.project.librarymanagementsystem.books;

import com.project.librarymanagementsystem.records.Record;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;

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

    @NotNull
    @Size(min = 5, max = 255)
    private String title;

    @NotNull
    @ISBN(message = "Invalid ISBN")
    private String ISBN;

    private String publisher;

    private String author;

    private String genre;

    private String language;

    @Column(name = "number_of_pages")
    @Min(1)
    private int numberOfPages;

    @OneToMany(mappedBy = "book")
    private List<Record> records;
}
