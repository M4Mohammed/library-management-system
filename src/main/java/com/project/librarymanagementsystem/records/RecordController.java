package com.project.librarymanagementsystem.records;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    //try catch maybe ????
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Record> borrowBook(
            @PathVariable UUID bookId,
            @PathVariable UUID patronId
    ) {
        Record record = recordService.borrowBook(bookId, patronId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(record);
    }

    //try catch maybe ????
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Record> returnBook(
            @PathVariable UUID bookId,
            @PathVariable UUID patronId
    ) {
        Record record = recordService.returnBook(bookId, patronId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(record);
    }

}
