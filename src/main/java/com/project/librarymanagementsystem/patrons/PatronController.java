package com.project.librarymanagementsystem.patrons;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patronService.getAllPatrons());
    }

    @GetMapping("{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patronService.getPatronById(id));
    }

    @PostMapping
    public ResponseEntity<Patron> createPatron(@Valid @RequestBody Patron patron) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(patronService.createPatron(patron));
    }

    @PutMapping("{id}")
    public ResponseEntity<Patron> updatePatron(
            @PathVariable UUID id,
            @RequestBody Patron patron
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patronService.updatePatron(id, patron));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable UUID id) {
        patronService.deletePatron(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
