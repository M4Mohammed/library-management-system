package com.project.librarymanagementsystem.patrons;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends CrudRepository<Patron, Long> {
}
