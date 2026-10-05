package com.microserrvices.loan.controller;

import com.microserrvices.loan.model.Loan;
import com.microserrvices.loan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/issue")
    public ResponseEntity<Loan> issueBook(@RequestParam String bookId, @RequestParam String memberId) {
        return ResponseEntity.ok(loanService.issueLoan(bookId, memberId));
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable String loanId) {
        return loanService.returnLoan(loanId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String loanId) {
        return loanService.getLoanById(loanId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/member/{memberId}")
    public List<Loan> getLoansByMember(@PathVariable String memberId) {
        return loanService.getLoansByMemberId(memberId);
    }
}
