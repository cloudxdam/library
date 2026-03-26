package com.pachedev.library.dto.book;

public record BookResponse(
                Long id,
                String title,
                String author,
                String isbn,
                Integer pages) {
}