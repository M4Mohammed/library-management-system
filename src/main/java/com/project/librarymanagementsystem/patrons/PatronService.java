package com.project.librarymanagementsystem.patrons;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepo patronRepository;


}
