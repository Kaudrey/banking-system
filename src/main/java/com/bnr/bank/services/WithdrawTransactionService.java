package com.bnr.bank.services;

import com.bnr.bank.models.WithdrawTransaction;
import java.util.UUID;


public interface WithdrawTransactionService {
    WithdrawTransaction withdrawMoney(UUID customerId, Double amount);
}
