package com.pachedev.library.dto.loan;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record UpdateLoanDateRequest(
                @NotNull(message = "Loan date is required") LocalDate loanDate) {

}
