package com.microserrvices.loan.repository;

import com.microserrvices.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByMemberId(String memberId);
    List<Loan> findByBookId(String bookId);
}
