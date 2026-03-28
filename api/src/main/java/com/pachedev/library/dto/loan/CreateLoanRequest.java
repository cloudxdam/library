package com.pachedev.library.dto.loan;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CreateLoanRequest(
        @NotNull(message = "Loan date is required") LocalDate loanDate,
        @NotNull(message = "User is required") Long userId,
        @NotNull(message = "Book is required") Long bookId) {

}
