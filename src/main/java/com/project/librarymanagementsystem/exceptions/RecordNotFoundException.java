package com.project.librarymanagementsystem.exceptions;


import java.util.UUID;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(UUID id) {
        super("Borrow not found with id: " + id);
    }
}
