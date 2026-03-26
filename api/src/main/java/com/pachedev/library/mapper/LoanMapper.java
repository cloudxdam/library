package com.pachedev.library.mapper;

import org.springframework.stereotype.Component;

import com.pachedev.library.dto.loan.LoanResponse;
import com.pachedev.library.model.Loan;

@Component
public class LoanMapper {

    public LoanResponse toResponse(Loan loan) {
        if (loan == null) {
            return null;
        }
        return new LoanResponse(
                loan.getId(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getUser().getId(),
                loan.getUser().getName(),
                loan.getBook().getId(),
                loan.getBook().getTitle());
    }
}
