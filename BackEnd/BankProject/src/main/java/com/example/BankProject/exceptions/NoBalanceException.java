package com.example.BankProject.exceptions;

public class NoBalanceException extends RuntimeException {
    public NoBalanceException(String message) {
        super(message);
    }
}
