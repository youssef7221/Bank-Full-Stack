package com.example.BankProject.repository;

import com.example.BankProject.entity.Account;
import com.example.BankProject.entity.TransactionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionData, Integer> {
    List<TransactionData> findByAccountId(Account account);
}
