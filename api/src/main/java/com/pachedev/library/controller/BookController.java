package com.pachedev.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pachedev.library.dto.book.BookResponse;
import com.pachedev.library.dto.book.CreateBookRequest;
import com.pachedev.library.dto.book.ReplaceBookRequest;
import com.pachedev.library.dto.book.UpdateBookRequest;
import com.pachedev.library.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookResponse createdBook = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));

    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
            @Valid @RequestBody ReplaceBookRequest request) {
        BookResponse updatedBook = bookService.update(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> patchBook(@PathVariable Long id,
            @Valid @RequestBody UpdateBookRequest request) {
        BookResponse updatedBook = bookService.patchUpdate(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> findBookByAuthorAndPages(@RequestParam String author,
            @RequestParam Integer pages) {
        return ResponseEntity.ok(bookService.findByAuthorAndPages(author, pages));
    }

    @GetMapping("/range")
    public ResponseEntity<List<BookResponse>> findByPagesBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return ResponseEntity.ok(bookService.findByPagesBetween(min, max));
    }
}
