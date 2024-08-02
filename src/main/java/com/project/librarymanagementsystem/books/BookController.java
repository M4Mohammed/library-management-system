package com.project.librarymanagementsystem.books;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getAllBooks());
    }

    @GetMapping(path = "{Id}")
    public ResponseEntity<Book> getBookById(@PathVariable("Id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(Book book) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.addBook(book));
    }

    @PutMapping(path = "{Id}")
    public ResponseEntity<Book> updateBook(@PathVariable("Id") UUID id, Book book) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.updateBook(id, book));
    }

    @DeleteMapping(path = "{Id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("Id") UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



}
