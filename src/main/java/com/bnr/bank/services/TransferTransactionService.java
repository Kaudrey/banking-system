package com.bnr.bank.services;

import com.bnr.bank.models.TransferTransaction;

import java.util.UUID;

public interface TransferTransactionService {
    TransferTransaction transferMoney(UUID senderId, UUID receiverId, Double amount);
}
