package com.pachedev.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pachedev.library.dto.loan.CreateLoanRequest;
import com.pachedev.library.dto.loan.LoanResponse;
import com.pachedev.library.dto.loan.UpdateLoanDateRequest;
import com.pachedev.library.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> findLoanById(@PathVariable Long id) {
            return ResponseEntity.ok(loanService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> findAllLoans() {
        return ResponseEntity.ok(loanService.findAll());

    }

    @PatchMapping("/{id}/loan-date")
    public ResponseEntity<LoanResponse> updateLoanDate(@PathVariable Long id, @Valid @RequestBody UpdateLoanDateRequest request) {
            return ResponseEntity.ok(loanService.updateLoanDate(id, request));

    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {
            return ResponseEntity.ok(loanService.returnLoan(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
            loanService.delete(id);
            return ResponseEntity.noContent().build();
    }
}
