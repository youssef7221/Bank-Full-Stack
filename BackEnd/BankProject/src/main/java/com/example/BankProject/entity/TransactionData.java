package com.example.BankProject.entity;

import com.example.BankProject.entity.constantEnum.TransactionalType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false, length = 255)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionalType transactionalType;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name ="account_id", nullable = false)
    private Account accountId;
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TransactionalType getTransactionalType() {
        return transactionalType;
    }

    public void setTransactionalType(TransactionalType transactionalType) {
        this.transactionalType = transactionalType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTransactionDate() {
        this.createdAt = LocalDateTime.now();
    }

    public Account getAccountId() {  // Renamed getter
        return accountId;
    }

    public void setAccountId(Account accountId) {  // Renamed setter
        this.accountId = accountId;
    }
}
