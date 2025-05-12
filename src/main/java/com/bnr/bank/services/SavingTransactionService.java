package com.bnr.bank.services;

import com.bnr.bank.models.SavingTransaction;

import java.util.UUID;

public interface SavingTransactionService {
    SavingTransaction saveMoney(UUID customerId, Double amount);
}
