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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "ISBN already exists")
    })
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookResponse createdBook = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);

    }

    @Operation(summary = "Get a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));

    }

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @Operation(summary = "Replace a book (PUT)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "ISBN already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
            @Valid @RequestBody ReplaceBookRequest request) {
        BookResponse updatedBook = bookService.update(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Partially update a book (PATCH)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "ISBN already exists")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> patchBook(@PathVariable Long id,
            @Valid @RequestBody UpdateBookRequest request) {
        BookResponse updatedBook = bookService.patchUpdate(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search books by author and pages")
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> findBookByAuthorAndPages(@RequestParam String author,
            @RequestParam Integer pages) {
        return ResponseEntity.ok(bookService.findByAuthorAndPages(author, pages));
    }

    @Operation(summary = "Find books within a page range")
    @GetMapping("/range")
    public ResponseEntity<List<BookResponse>> findByPagesBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return ResponseEntity.ok(bookService.findByPagesBetween(min, max));
    }
}
