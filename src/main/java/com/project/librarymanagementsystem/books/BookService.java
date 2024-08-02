package com.project.librarymanagementsystem.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

        private final BookRepository bookRepository;
}
