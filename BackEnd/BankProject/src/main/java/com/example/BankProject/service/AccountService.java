package com.example.BankProject.service;

import com.example.BankProject.entity.Account;
import com.example.BankProject.exceptions.AccountNotFoundException;
import com.example.BankProject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getUsers() {
        return accountRepository.findAll();
    }

    public double getBalance(Integer id) {
        Account account = accountRepository.findAccountByAccountId(id);
        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return account.getBalance();
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    public Optional<Account> loginAccount(String cardNumber, String password) {
        Optional<Account> account = accountRepository.findAccountByCardNumberAndPassword(cardNumber, password);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Invalid card number or password");
        }
        return account;
    }
}
