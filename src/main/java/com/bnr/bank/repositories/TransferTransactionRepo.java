package com.bnr.bank.repositories;

import com.bnr.bank.models.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferTransactionRepo extends JpaRepository<TransferTransaction, UUID> {

}
