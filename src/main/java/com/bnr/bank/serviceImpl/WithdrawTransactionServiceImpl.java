package com.bnr.bank.serviceImpl;


import com.bnr.bank.models.Customer;
import com.bnr.bank.models.SavingTransaction;
import com.bnr.bank.models.WithdrawTransaction;
import com.bnr.bank.repositories.CustomerRepo;

import com.bnr.bank.repositories.WithdrawTransactionRepo;
import com.bnr.bank.services.WithdrawTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WithdrawTransactionServiceImpl implements WithdrawTransactionService {

    @Autowired
    private WithdrawTransactionRepo withdrawRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public WithdrawTransaction withdrawMoney(UUID customerId, Double amount) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Ensure that the amount is positive
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
        if (customer.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balance
        customer.setBalance(customer.getBalance() - amount);
        customer.setLastUpdateDateTime(LocalDateTime.now());
        customerRepo.save(customer);

        // Save transaction
        WithdrawTransaction transaction = new WithdrawTransaction();
        transaction.setCustomer(customer);
        transaction.setAccount(customer.getAccount());
        transaction.setAmount(amount);
        transaction.setBankingDateTime(LocalDateTime.now());
        withdrawRepo.save(transaction);
        return transaction;
    }
}
