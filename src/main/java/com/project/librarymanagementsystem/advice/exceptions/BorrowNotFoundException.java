package com.project.librarymanagementsystem.advice.exceptions;


import java.util.UUID;

public class BorrowNotFoundException extends RuntimeException {

    public BorrowNotFoundException(UUID id) {
        super("Borrow not found with id: " + id);
    }
}
