package com.project.librarymanagementsystem.exceptions;

import java.util.UUID;

public class PatronNotFoundException extends RuntimeException {

    public PatronNotFoundException(UUID id) {
        super("Patron not found with id: " + id);
    }
}
