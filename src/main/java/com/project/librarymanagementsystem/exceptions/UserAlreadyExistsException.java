package com.project.librarymanagementsystem.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(String email, String identifierType) {
        super(String.format("User already exists with %s: %s", identifierType, email));
    }
}