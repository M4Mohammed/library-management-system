package com.project.librarymanagementsystem.patrons;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Embeddable
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
}
