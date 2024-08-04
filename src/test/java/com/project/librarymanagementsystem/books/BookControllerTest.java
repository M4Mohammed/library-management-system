package com.project.librarymanagementsystem.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .ISBN("1234567890123")
                .publisher("Test Publisher")
                .genre("Test Genre")
                .language("English")
                .numberOfPages(100)
                .isAvailable(true)
                .build();
    }

    @Test
    void getAllBooks() throws Exception {
        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(book.getTitle()));
    }

    @Test
    void getBookById() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(bookService.getBookById(id)).thenReturn(book);

        mvc.perform(get("/api/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    void checkBookAvailability() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(bookService.checkBookAvailability(id)).thenReturn(true);

        mvc.perform(get("/api/books/{id}/availability", id))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser
    void addBook() throws Exception {
        Mockito.when(bookService.addBook(any(Book.class))).thenReturn(book);

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    @WithMockUser
    void updateBook() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(bookService.updateBook(any(UUID.class), any(Book.class))).thenReturn(book);

        mvc.perform(put("/api/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    @WithMockUser
    void deleteBook() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/books/{id}", id))
                .andExpect(status().isNoContent());
    }

}
