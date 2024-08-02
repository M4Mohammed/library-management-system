package com.project.librarymanagementsystem;

import com.project.librarymanagementsystem.exceptions.BookNotFoundException;
import com.project.librarymanagementsystem.exceptions.ErrorObj;
import com.project.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            PatronNotFoundException.class,
            BookNotFoundException.class
    })
    public ResponseEntity<ErrorObj> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorObj.builder()
                                .error(e.getClass().getSimpleName())
                                .message(e.getMessage())
                                .build()
                );
    }
}
