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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loans", description = "Operations related to loans")
public class LoanController {

        private final LoanService loanService;

        public LoanController(LoanService loanService) {
                this.loanService = loanService;
        }

        @Operation(summary = "Create a new loan")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Loan created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data or business rule violation"),
                        @ApiResponse(responseCode = "404", description = "User or book not found")
        })
        @PostMapping
        public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
                return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(request));
        }

        @Operation(summary = "Get a loan by ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Loan found"),
                @ApiResponse(responseCode = "404", description = "Loan not found")
        })
        @GetMapping("/{id}")
        public ResponseEntity<LoanResponse> findLoanById(@PathVariable Long id) {
                return ResponseEntity.ok(loanService.findById(id));
        }

        @Operation(summary = "Get all loans")
        @GetMapping
        public ResponseEntity<List<LoanResponse>> findAllLoans() {
                return ResponseEntity.ok(loanService.findAll());

        }

        @Operation(summary = "Update a loan date")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Loan date updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "404", description = "Loan not found")
        })
        @PatchMapping("/{id}/loan-date")
        public ResponseEntity<LoanResponse> updateLoanDate(@PathVariable Long id,
                        @Valid @RequestBody UpdateLoanDateRequest request) {
                return ResponseEntity.ok(loanService.updateLoanDate(id, request));

        }

        @Operation(summary = "Return a loan by setting the current date as return date")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Loan returned successfully"),
                        @ApiResponse(responseCode = "400", description = "Loan has already been returned"),
                        @ApiResponse(responseCode = "404", description = "Loan not found")
        })
        @PatchMapping("/{id}/return")
        public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {
                return ResponseEntity.ok(loanService.returnLoan(id));
        }

        @Operation(summary = "Delete a returned loan")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Loan deleted successfully"),
                        @ApiResponse(responseCode = "400", description = "Cannot delete an active loan"),
                        @ApiResponse(responseCode = "404", description = "Loan not found")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
                loanService.delete(id);
                return ResponseEntity.noContent().build();
        }
}
