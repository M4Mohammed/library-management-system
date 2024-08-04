package com.project.librarymanagementsystem.patrons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.librarymanagementsystem.records.Record;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 2, max = 255)
    private String lastName;

    @Embedded
    private Address address;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid mobile number")
    private String mobile;

    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "patron")
    private List<Record> records;


}
