package com.pachedev.library.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateBookRequest(
                @Size(max = 100, message = "Title cannot exceed 100 characters") String title,
                @Size(max = 50, message = "Author cannot exceed 50 characters") String author,
                @Size(max = 20, message = "ISBN cannot exceed 20 characters") String isbn,
                @Min(value = 1, message = "Number of pages must be at least 1") Integer pages) {
}
