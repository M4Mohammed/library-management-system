package com.project.librarymanagementsystem.exceptions;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(UUID id) {
        super("Book not found with id: " + id);
    }
}
