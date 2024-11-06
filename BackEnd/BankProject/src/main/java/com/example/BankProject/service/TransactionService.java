package com.example.BankProject.service;

import com.example.BankProject.entity.Account;
import com.example.BankProject.entity.TransactionData;
import com.example.BankProject.entity.constantEnum.TransactionalType;
import com.example.BankProject.exceptions.AccountNotFoundException;
import com.example.BankProject.exceptions.NoBalanceException;
import com.example.BankProject.repository.AccountRepository;
import com.example.BankProject.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    public TransactionData withDraw(int accountId, int amount) {
        Account fromAccount = accountRepository.findAccountByAccountId(accountId);
        if (fromAccount == null){
            throw new AccountNotFoundException("Account not found");
        }
        if (fromAccount.getBalance() < amount) {
            throw new NoBalanceException("Insufficient balance");
        }
        if (amount < 0) {
            throw new NoBalanceException("Amount must be positive");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        TransactionData transactionData = new TransactionData();
        transactionData.setTransactionalType(TransactionalType.WITHDRAW);
        transactionData.setAmount(amount);
        transactionData.setNotes("Transfer from account Id " + accountId + ": " + amount + " made successfully");
        transactionData.setAccountId(fromAccount);
        transactionData.setTransactionDate();
        return transactionRepository.save(transactionData);
    }
    public TransactionData deposit(int accountId, int amount) {
        Account toAccount = accountRepository.findAccountByAccountId(accountId);
        if (toAccount == null){
            throw new AccountNotFoundException("Account not found");
        }
        if (amount < 0){
            throw new NoBalanceException("Amount must be positive");
        }
        toAccount.setBalance(toAccount.getBalance() + amount);
        TransactionData transactionData = new TransactionData();
        transactionData.setTransactionalType(TransactionalType.DEPOSIT);
        transactionData.setAmount(amount);
        transactionData.setNotes("Transfer to account Id " + accountId + ": " + amount + " made successfully");
        transactionData.setAccountId(toAccount);
        transactionData.setTransactionDate();
        return transactionRepository.save(transactionData);
    }

    public List<TransactionData> getTransactionHistory(int id) {
        Account accountOptional = accountRepository.findAccountByAccountId(id);
        if (accountOptional == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return transactionRepository.findByAccountId(accountOptional);
    }
}
