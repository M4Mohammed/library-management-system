package com.project.librarymanagementsystem.advice.exceptions;

import java.util.UUID;

public class PatronNotFoundException extends RuntimeException {

    public PatronNotFoundException(UUID id) {
        super("Patron not found with id: " + id);
    }
}
