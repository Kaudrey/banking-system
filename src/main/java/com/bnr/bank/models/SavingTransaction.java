package com.bnr.bank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "saving_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingTransaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account", nullable = false)
    private String account;

    @Positive(message = "Amount must be greater than zero")
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "type", nullable = false)
    private String type = "saving";

    @Column(name = "banking_date_time", nullable = false)
    private LocalDateTime bankingDateTime;
}
