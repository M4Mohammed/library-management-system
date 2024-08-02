package com.project.librarymanagementsystem.patrons;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(UUID id) {
        return patronRepository.findById(id).orElse(null);
    }

    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(UUID id, Patron patron) {
        Patron existingPatron = patronRepository.findById(id).orElseThrow();

        existingPatron.setFirstName(patron.getFirstName());
        existingPatron.setLastName(patron.getLastName());
        existingPatron.setAddress(patron.getAddress());
        existingPatron.setMobile(patron.getMobile());
        existingPatron.setEmail(patron.getEmail());
        return patronRepository.save(existingPatron);
    }

    public void deletePatron(UUID id) {
        patronRepository.deleteById(id);
    }
}
