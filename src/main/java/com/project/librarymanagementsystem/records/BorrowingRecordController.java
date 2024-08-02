package com.project.librarymanagementsystem.records;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    //try catch maybe ????
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
            @PathVariable UUID bookId,
            @PathVariable UUID patronId
    ) {
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(borrowingRecord);
    }

    //try catch maybe ????
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(
            @PathVariable UUID bookId,
            @PathVariable UUID patronId
    ) {
        BorrowingRecord borrowingRecord = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(borrowingRecord);
    }

}
