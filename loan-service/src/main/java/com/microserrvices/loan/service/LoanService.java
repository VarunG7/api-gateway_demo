package com.microserrvices.loan.service;

import com.microserrvices.loan.model.Loan;
import com.microserrvices.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private static final int DEFAULT_LOAN_DAYS = 14;
    private static final double DAILY_FINE_RATE = 2.0;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Transactional
    public Loan issueLoan(String bookId, String memberId) {
        String loanId = UUID.randomUUID().toString();
        LocalDate now = LocalDate.now();
        LocalDate dueDate = now.plusDays(DEFAULT_LOAN_DAYS);

        Loan loan = new Loan(
                loanId,
                bookId,
                memberId,
                now,
                dueDate,
                null,
                "ISSUED",
                0.0
        );

        return loanRepository.save(loan);
    }

    @Transactional
    public Optional<Loan> returnLoan(String loanId) {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        if (optionalLoan.isEmpty()) {
            return Optional.empty();
        }

        Loan loan = optionalLoan.get();
        if ("RETURNED".equalsIgnoreCase(loan.getStatus())) {
            return Optional.of(loan);
        }

        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);
        loan.setStatus("RETURNED");

        if (returnDate.isAfter(loan.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
            loan.setFineAmount(overdueDays * DAILY_FINE_RATE);
        } else {
            loan.setFineAmount(0.0);
        }

        return Optional.of(loanRepository.save(loan));
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(String loanId) {
        return loanRepository.findById(loanId);
    }

    public List<Loan> getLoansByMemberId(String memberId) {
        return loanRepository.findByMemberId(memberId);
    }
}
