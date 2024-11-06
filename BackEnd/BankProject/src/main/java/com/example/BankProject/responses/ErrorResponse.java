package com.example.BankProject.responses;

import java.time.LocalDateTime;

public class ErrorResponse {
    public String status ;
    public String message;
    public LocalDateTime timestamp;
    public ErrorResponse(String message) {
        this.status = "failed" ;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}