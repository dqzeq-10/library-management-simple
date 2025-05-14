package com.example.librarymanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Fines")
public class Fine {
    @Id
    @Column(name = "FineID", length = 10)
    private String fineId;

    @Column(name = "LoanID", nullable = false, length = 10)
    private String loanId;

    @Column(name = "Amount", nullable = false, precision = 10, length = 10)
    private Double amount;

    @Column(name = "Paid", nullable = false)
    private Boolean paid;

    // Getters and Setters

    public String getFineId() {
        return fineId;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}