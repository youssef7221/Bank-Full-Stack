package com.example.BankProject.responses;

public class SuccessResponse<T> {

    public String message;
    public String status;
    public T data;

    public SuccessResponse( T data ) {
       this.data = data;
        this.status = "success";
    }

    public SuccessResponse(String message, T data) {
        this.status  = "success";
        this.message = message;
        this.data = data;
    }
}

