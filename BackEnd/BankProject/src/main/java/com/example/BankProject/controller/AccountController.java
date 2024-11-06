package com.example.BankProject.controller;

import com.example.BankProject.entity.Account;
import com.example.BankProject.entity.dto.LoginRequest;
import com.example.BankProject.responses.SuccessResponse;
import com.example.BankProject.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/banking/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/get")
    public List<Account> getAccount() {
        return accountService.getUsers();
    }
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Account>> createAccount(@RequestBody @Valid Account account) {
        accountService.saveAccount(account);
        SuccessResponse<Account> response = new SuccessResponse<>(
                "Account created successfully",
                account
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/balance/{id}")
    public ResponseEntity<SuccessResponse<Double>> getBalance(@PathVariable int id) {
            double balance = accountService.getBalance(id);
            SuccessResponse<Double> response = new SuccessResponse<>(
                    balance
            );
            return new ResponseEntity<>(response , HttpStatus.OK) ;
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAllAccounts() {
        accountService.deleteAllAccounts();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All accounts deleted successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Account>> login(@RequestBody LoginRequest loginRequest) {
        Optional<Account> account = accountService.loginAccount(loginRequest.getCardNumber(),
                loginRequest.getPassword()
        );
        SuccessResponse<Account> successResponse = new SuccessResponse<>(
                "Login successful! Welcome, " + account.get().getUserName(),
                account.get()
        );
       return new ResponseEntity<>(successResponse,HttpStatus.CREATED);
    }
}
