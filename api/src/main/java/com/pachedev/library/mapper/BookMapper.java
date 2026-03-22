package com.pachedev.library.mapper;

import org.springframework.stereotype.Component;

import com.pachedev.library.dto.book.BookResponse;
import com.pachedev.library.dto.book.CreateBookRequest;
import com.pachedev.library.dto.book.UpdateBookRequest;
import com.pachedev.library.model.Book;

@Component
public class BookMapper {

    public Book toEntity(CreateBookRequest request) {
        if (request == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setPages(request.pages());

        return book;
    }

    public BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }

        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPages());
    }

    public void updateEntityFromRequest(UpdateBookRequest request, Book book) {
        if (request == null || book == null) {
            return;
        }

        if (request.title() != null) {
            book.setTitle(request.title());
        }

        if (request.author() != null) {
            book.setAuthor(request.author());
        }

        if (request.isbn() != null) {
            book.setIsbn(request.isbn());
        }

        if (request.pages() != null) {
            book.setPages(request.pages());
        }
    }
}
