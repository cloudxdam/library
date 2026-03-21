package com.pachedev.library.controller;

import java.util.List;
import java.util.Map;

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

import com.pachedev.library.model.Loan;
import com.pachedev.library.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan newLoan) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(newLoan));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findLoanById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<Loan> findAllLoans() {
        return loanService.findAll();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLoanDate(@PathVariable Long id, @RequestBody Loan updatedLoan) {
        try {
            return ResponseEntity.ok(loanService.updateLoanDate(id, updatedLoan));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<?> returnLoan(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanService.returnLoan(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long id) {
        try {
            loanService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
