package com.example.BankProject.controller;

import com.example.BankProject.entity.TransactionData;
import com.example.BankProject.responses.SuccessResponse;
import com.example.BankProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/banking/transaction")
public class TransactionController {
@Autowired
private TransactionService transactionService;
@PostMapping("/withdraw/{accountId}")
    public ResponseEntity<SuccessResponse<TransactionData>>
    withdraw(@PathVariable int accountId,@RequestParam int amount) {
   TransactionData transactionData = transactionService.withDraw(accountId, amount);
    SuccessResponse<TransactionData> successResponse = new SuccessResponse<>(
            "Withdraw Successfully",
            transactionData
    );
    return new ResponseEntity<>(successResponse, HttpStatus.ACCEPTED);
}
   @PostMapping("/deposit/{accountId}")
    public ResponseEntity<SuccessResponse<TransactionData>>
    deposit(@PathVariable int accountId,@RequestParam int amount) {
        TransactionData transactionData = transactionService.deposit(accountId, amount);
        SuccessResponse<TransactionData> successResponse = new SuccessResponse<>(
                "Deposit Successfully",
                transactionData
        );
        return new ResponseEntity<>(successResponse, HttpStatus.ACCEPTED);
    }
 @GetMapping("/history/{accountId}")
    public ResponseEntity<List<TransactionData>> transactionHistory(@PathVariable int accountId){
    List<TransactionData> transactionData = transactionService.getTransactionHistory(accountId);
    return new ResponseEntity<>(transactionData, HttpStatus.OK);
 }
}
