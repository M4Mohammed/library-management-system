package com.project.librarymanagementsystem.patrons;

import com.project.librarymanagementsystem.exceptions.PatronNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "patronCache", key = "#id")
    public Patron getPatronById(UUID id) {
        return patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));
    }

    @Cacheable(value = "patronCache", key = "#patron.id")
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Cacheable(value = "patronCache", key = "#id")
    public Patron updatePatron(UUID id, Patron patron) {
        Patron existingPatron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));

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
