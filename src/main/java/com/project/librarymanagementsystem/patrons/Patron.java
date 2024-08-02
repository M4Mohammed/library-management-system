package com.project.librarymanagementsystem.patrons;

import com.project.librarymanagementsystem.records.BorrowingRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private Address Address;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "patron")
    private List<BorrowingRecord> borrowingRecords;


}
