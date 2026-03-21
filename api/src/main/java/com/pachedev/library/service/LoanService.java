package com.pachedev.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pachedev.library.model.Loan;
import com.pachedev.library.repository.LoanRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan create(Loan newLoan) {

        if (loanRepository.existsByBookIdAndReturnDateIsNull(newLoan.getBook().getId())) {
            throw new IllegalArgumentException("This book is already on loan");
        }

        if (loanRepository.countByUserIdAndReturnDateIsNull(newLoan.getUser().getId()) >= 3) {
            throw new IllegalArgumentException("This user already has 3 active loans");
        }

        return loanRepository.save(newLoan);
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with Id: " + id));
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan updateLoanDate(Long id, Loan updated) {
        Loan existingLoan = findById(id);
        existingLoan.setLoanDate(updated.getLoanDate());
        return loanRepository.save(existingLoan);
    }

    public Loan returnLoan(Long id) {
        Loan existingLoan = findById(id);

        if (existingLoan.getReturnDate() != null) {
            throw new IllegalArgumentException("This loan has been already returned");
        }
        existingLoan.setReturnDate(LocalDate.now());
        return loanRepository.save(existingLoan);
    }

    public void delete(Long id) {
        Loan existingLoan = findById(id);

        if (existingLoan.getReturnDate() == null) {
            throw new IllegalArgumentException("Cannot delete an active loan");
        }
        loanRepository.delete(existingLoan);
    }
}
