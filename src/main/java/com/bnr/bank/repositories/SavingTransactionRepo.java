package com.bnr.bank.repositories;

import com.bnr.bank.models.SavingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SavingTransactionRepo extends JpaRepository<SavingTransaction, UUID> {

}
