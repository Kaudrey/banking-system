package com.bnr.bank.serviceImpl;

import com.bnr.bank.models.Customer;
import com.bnr.bank.models.TransferTransaction;
import com.bnr.bank.repositories.CustomerRepo;
import com.bnr.bank.repositories.TransferTransactionRepo;
import com.bnr.bank.services.TransferTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransferTransactionServiceImpl implements TransferTransactionService {

    private final TransferTransactionRepo transactionRepo;
    private final CustomerRepo customerRepo;

    public TransferTransactionServiceImpl(TransferTransactionRepo transactionRepo, CustomerRepo customerRepo) {
        this.transactionRepo = transactionRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public TransferTransaction transferMoney(UUID senderId, UUID receiverId, Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Cannot transfer money to yourself");
        }

        Customer sender = customerRepo.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Customer receiver = customerRepo.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct from sender
        sender.setBalance(sender.getBalance() - amount);
        sender.setLastUpdateDateTime(LocalDateTime.now());
        customerRepo.save(sender);

        // Credit to receiver
        receiver.setBalance(receiver.getBalance() + amount);
        receiver.setLastUpdateDateTime(LocalDateTime.now());
        customerRepo.save(receiver);

        // Save transaction
        TransferTransaction transaction = new TransferTransaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepo.save(transaction);
    }
}
