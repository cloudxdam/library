package com.pachedev.library.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
                @NotBlank(message = "Name is required") @Size(max = 60, message = "Name cannot exceed 60 characters") String name,
                @NotBlank(message = "Email is required") @Email(message = "Email must be valid") @Size(max = 100, message = "Email cannot exceed 100 characters") String email) {
}
