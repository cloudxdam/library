package com.pachedev.library.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
                @Size(max = 60, message = "Name cannot exceed 60 characters") String name,
                @Email(message = "Email must be valid") @Size(max = 100, message = "Email cannot exceed 100 characters") String email) {
}
