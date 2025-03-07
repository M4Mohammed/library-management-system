package com.project.librarymanagementsystem.books;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
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

    @GetMapping(path = "{Id}/availability")
    public ResponseEntity<Boolean> checkBookAvailability(@PathVariable("Id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.checkBookAvailability(id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.addBook(book));
    }

    @PutMapping(path = "{Id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable("Id") UUID id,
            @RequestBody Book book
    ) {
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
