package com.bnr.bank.repositories;

import com.bnr.bank.models.WithdrawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WithdrawTransactionRepo extends JpaRepository<WithdrawTransaction, UUID> {
}
