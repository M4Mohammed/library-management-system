package com.project.librarymanagementsystem.patrons.dto;

import com.project.librarymanagementsystem.patrons.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.project.librarymanagementsystem.patrons.Patron}
 */
public record PatronResponseDto(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt,
                                @NotNull @Size(min = 2, max = 255) String firstName,
                                @NotNull @Size(min = 2, max = 255) String lastName, Address address,
                                @Pattern(message = "Invalid mobile number", regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$") String mobile,
                                @Pattern(message = "Invalid email", regexp = "^(.+)@(.+)$") String email) implements Serializable {
}