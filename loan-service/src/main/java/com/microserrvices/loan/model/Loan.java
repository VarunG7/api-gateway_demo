package com.microserrvices.loan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @Column(nullable = false, updatable = false)
    private String loanId;

    @Column(nullable = false)
    private String bookId;

    @Column(nullable = false)
    private String memberId;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status; // ISSUED, RETURNED, OVERDUE
    private double fineAmount;
}
