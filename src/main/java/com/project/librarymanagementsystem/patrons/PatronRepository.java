package com.project.librarymanagementsystem.patrons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatronRepository extends JpaRepository<Patron, UUID> {
}
