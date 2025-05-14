package com.bnr.bank.serviceImpl;


import com.bnr.bank.models.Customer;
import com.bnr.bank.models.SavingTransaction;
import com.bnr.bank.models.WithdrawTransaction;
import com.bnr.bank.repositories.CustomerRepo;

import com.bnr.bank.repositories.WithdrawTransactionRepo;
import com.bnr.bank.services.EmailService;
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

    @Autowired
    private EmailService emailService;

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

        String subject = "Withdraw Confirmation - BNR Bank";
        String body = String.format("""
                Hello %s,

                This is to confirm that you've withdrew %.2f RWF into your account: %s.
                Your new balance is: %.2f RWF.

                Date: %s

                Thank you for banking with us!

                BNR Bank.
                """,
                customer.getFirstName(), // Make sure your Customer model has getFullName() or similar
                amount,
                customer.getAccount(),
                customer.getBalance(),
                transaction.getBankingDateTime()
        );

        emailService.sendTransactionConfirmation(customer.getEmail(), subject, body);

        return transaction;
    }
}