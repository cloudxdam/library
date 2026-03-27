package com.pachedev.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pachedev.library.dto.book.BookResponse;
import com.pachedev.library.dto.book.CreateBookRequest;
import com.pachedev.library.dto.book.ReplaceBookRequest;
import com.pachedev.library.dto.book.UpdateBookRequest;
import com.pachedev.library.exception.DuplicateResourceException;
import com.pachedev.library.exception.ResourceNotFoundException;
import com.pachedev.library.mapper.BookMapper;
import com.pachedev.library.model.Book;
import com.pachedev.library.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookResponse create(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException("A book with ISBN " + request.isbn() + " already exists");
        }

        Book newBook = bookMapper.toEntity(request);
        Book savedBook = bookRepository.save(newBook);
        return bookMapper.toResponse(savedBook);
    }

    public BookResponse findById(Long id) {
        Book book = findBookEntityById(id);
        return bookMapper.toResponse(book);

    }

    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> responseList = fillResponseList(books);

        return responseList;
    }

    private Book findBookEntityById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return book;
    }

    public BookResponse update(Long id, ReplaceBookRequest request) {
        Book existingBook = findBookEntityById(id);

        validateIsbnForUpdate(request.isbn(), existingBook);

        bookMapper.replaceEntityFromRequest(request, existingBook);
        Book updatedBook = bookRepository.save(existingBook);

        return bookMapper.toResponse(updatedBook);
    }

    private void validateIsbnForUpdate(String newIsbn, Book existingBook) {
        if (!newIsbn.equals(existingBook.getIsbn())
                && bookRepository.existsByIsbn(newIsbn)) {
            throw new DuplicateResourceException("A book with ISBN " + newIsbn + " already exists");
        }
    }

    public BookResponse patchUpdate(Long id, UpdateBookRequest request) {
        Book existingBook = findBookEntityById(id);

        if (request.isbn() != null) {
            validateIsbnForUpdate(request.isbn(), existingBook);
        }

        bookMapper.updateEntityFromRequest(request, existingBook);

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toResponse(updatedBook);
    }

    public void delete(Long id) {
        Book existingBook = findBookEntityById(id);
        bookRepository.delete(existingBook);
    }

    public List<BookResponse> findByAuthorAndPages(String author, Integer pages) {

        List<Book> books = bookRepository.findByAuthorAndPages(author, pages);
        List<BookResponse> responseList = fillResponseList(books);

        return responseList;
    }

    private List<BookResponse> fillResponseList(List<Book> books) {
        List<BookResponse> responseList = new ArrayList<>();

        for (Book book : books) {
            responseList.add(bookMapper.toResponse(book));
        }
        return responseList;
    }

    public List<BookResponse> findByPagesBetween(Integer min, Integer max) {
        List<Book> books = bookRepository.findByPagesBetween(min, max);
        List<BookResponse> responseList = fillResponseList(books);

        return responseList;
    }

}