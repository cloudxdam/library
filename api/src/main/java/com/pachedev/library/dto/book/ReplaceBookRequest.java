package com.pachedev.library.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReplaceBookRequest(

                @NotBlank(message = "Title is required") @Size(max = 100, message = "Title cannot exceed 100 characters") String title,
                @NotBlank(message = "Author is required") @Size(max = 50, message = "Author cannot exceed 50 characters") String author,
                @NotBlank(message = "ISBN is required") @Size(max = 20, message = "ISBN cannot exceed 20 characters") String isbn,
                @NotNull(message = "Number of pages is required") @Min(value = 1, message = "Number of pages must be at least 1") Integer pages) {
}