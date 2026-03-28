package com.pachedev.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pachedev.library.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    int countByUserId(Long id);

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    boolean existsByUserIdAndReturnDateIsNull(Long userId);

    long countByUserIdAndReturnDateIsNull(Long userId);
}
