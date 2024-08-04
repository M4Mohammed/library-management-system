package com.project.librarymanagementsystem.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    BookService bookService;

    @Mock BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        this.bookService = new BookService(bookRepository);
    }

    @Test
    void getAllBooksShouldReturnAll() {
        Book book =
                Book.builder()
                        .title("Test Book")
                        .author("Test Author")
                        .ISBN("1234567890123")
                        .publisher("Test Publisher")
                        .genre("Test Genre")
                        .language("English")
                        .numberOfPages(100)
                        .isAvailable(true)
                        .build();

        Book book1 =
                Book.builder()
                        .title("Test Book 1")
                        .author("Test Author 1")
                        .ISBN("1234567890124")
                        .publisher("Test Publisher 1")
                        .genre("Test Genre 1")
                        .language("English")
                        .numberOfPages(100)
                        .isAvailable(true)
                        .build();

        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book, book1));
    }

}
