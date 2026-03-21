package com.pachedev.library.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pachedev.library.model.Book;
import com.pachedev.library.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book create(Book newBook) {
        if (bookRepository.existsByIsbn(newBook.getIsbn())) {
            throw new IllegalArgumentException("A book with ISBN " + newBook.getIsbn() + " already exists");
        }
        return bookRepository.save(newBook);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book update(Long id, Book updatedBook) {
        Book existingBook = findById(id);

        validateIsbnForUpdate(updatedBook.getIsbn(), existingBook);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPages(updatedBook.getPages());
        return bookRepository.save(existingBook);
    }

    private void validateIsbnForUpdate(String newIsbn, Book existingBook) {
        if (!newIsbn.equals(existingBook.getIsbn())
                && bookRepository.existsByIsbn(newIsbn)) {
            throw new IllegalArgumentException("A book with ISBN " + newIsbn + " already exists");
        }
    }

    public Book patchUpdate(Long id, Map<String, Object> updates) {
        Book existingBook = findById(id);

        if (updates.containsKey("title")) {
            existingBook.setTitle((String) updates.get("title"));
        }

        if (updates.containsKey("author")) {
            existingBook.setAuthor((String) updates.get("author"));
        }

        if (updates.containsKey("isbn")) {
            String newIsbn = (String) updates.get("isbn");
            validateIsbnForUpdate(newIsbn, existingBook);
            existingBook.setIsbn(newIsbn);
        }

        if (updates.containsKey("pages")) {
            Object value = updates.get("pages");

            if (value instanceof Number) {
                existingBook.setPages(((Number) value).intValue());
            } else {
                throw new IllegalArgumentException("Pages must be a number");
            }
        }

        return bookRepository.save(existingBook);

    }

    public void delete(Long id) {
        Book existingBook = findById(id);
        bookRepository.delete(existingBook);
    }

    public List<Book> findByAuthorAndPages(String author, Integer pages) {
        return bookRepository.findByAuthorAndPages(author, pages);
    }

    public List<Book> findByPagesBetween(Integer min, Integer max) {
        return bookRepository.findByPagesBetween(min, max);
    }

}