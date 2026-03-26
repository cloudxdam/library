package com.pachedev.library.dto.loan;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        LocalDate loanDate,
        LocalDate returnDate,
        Long userId,
        String userName,
        Long bookId,
        String bookTitle) {
}
