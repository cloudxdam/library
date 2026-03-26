package com.pachedev.library.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pachedev.library.dto.loan.CreateLoanRequest;
import com.pachedev.library.dto.loan.LoanResponse;
import com.pachedev.library.dto.loan.UpdateLoanDateRequest;
import com.pachedev.library.mapper.LoanMapper;
import com.pachedev.library.model.Book;
import com.pachedev.library.model.Loan;
import com.pachedev.library.model.User;
import com.pachedev.library.repository.BookRepository;
import com.pachedev.library.repository.LoanRepository;
import com.pachedev.library.repository.UserRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository,
            LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanMapper = loanMapper;
    }

    public LoanResponse create(CreateLoanRequest request) {

        User user = findUserEntityById(request.userId());
        Book book = findBookEntityById(request.bookId());

        if (loanRepository.existsByBookIdAndReturnDateIsNull(request.bookId())) {
            throw new IllegalArgumentException("This book is already on loan");
        }

        if (loanRepository.countByUserIdAndReturnDateIsNull(request.userId()) >= 3) {
            throw new IllegalArgumentException("This user already has 3 active loans");
        }

        Loan newLoan = new Loan();
        newLoan.setLoanDate(request.loanDate());
        newLoan.setReturnDate(null);
        newLoan.setUser(user);
        newLoan.setBook(book);

        Loan savedLoan = loanRepository.save(newLoan);
        return loanMapper.toResponse(savedLoan);
    }

    public LoanResponse findById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with Id: " + id));

        return loanMapper.toResponse(loan);
    }

    private Loan findLoanEntityById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with Id: " + id));
    }

    private Book findBookEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with Id: " + id));
    }

    private User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with Id: " + id));
    }

    public List<LoanResponse> findAll() {
        List<Loan> loans = loanRepository.findAll();
        List<LoanResponse> loanResponses = new ArrayList<>();

        for (Loan loan : loans) {
            loanResponses.add(loanMapper.toResponse(loan));
        }
        return loanResponses;
    }

    public LoanResponse updateLoanDate(Long id, UpdateLoanDateRequest request) {
        Loan existingLoan = findLoanEntityById(id);
        existingLoan.setLoanDate(request.loanDate());

        Loan savedLoan = loanRepository.save(existingLoan);
        return loanMapper.toResponse(savedLoan);
    }

    public LoanResponse returnLoan(Long id) {
        Loan existingLoan = findLoanEntityById(id);

        if (existingLoan.getReturnDate() != null) {
            throw new IllegalArgumentException("This loan has been already returned");
        }
        existingLoan.setReturnDate(LocalDate.now());
        Loan updatedLoan = loanRepository.save(existingLoan);
        return loanMapper.toResponse(updatedLoan);
    }

    public void delete(Long id) {
        Loan existingLoan = findLoanEntityById(id);

        if (existingLoan.getReturnDate() == null) {
            throw new IllegalArgumentException("Cannot delete an active loan");
        }
        loanRepository.delete(existingLoan);
    }
}
