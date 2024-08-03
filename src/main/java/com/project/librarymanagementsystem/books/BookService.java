package com.project.librarymanagementsystem.books;

import com.project.librarymanagementsystem.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

        private final BookRepository bookRepository;

        public List<Book> getAllBooks() {
            return bookRepository.findAll();
        }

        @Cacheable(value = "bookCache", key = "#id")
        public Book getBookById(UUID id) {
            return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        }

        @Cacheable(value = "bookCache", key = "#id")
        public boolean checkBookAvailability(UUID id) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
            return book.isAvailable();
        }

        @Cacheable(value = "bookCache", key = "#book.id")
        public Book addBook(Book book) {
            return bookRepository.save(book);
        }

        @Cacheable(value = "bookCache", key = "#id")
        public Book updateBook(UUID id, Book book) {
            Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

            existingBook.setTitle(book.getTitle());
            existingBook.setISBN(book.getISBN());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setLanguage(book.getLanguage());
            existingBook.setNumberOfPages(book.getNumberOfPages());

            return bookRepository.save(existingBook);
        }

        public void deleteBook (UUID id) {
            bookRepository.deleteById(id);
        }

}
