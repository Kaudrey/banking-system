package com.bnr.bank.repositories;

import com.bnr.bank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
//    void getAllCustomers();
    Optional<Customer> getCustomerById(UUID id);
//    Customer findByAccountNumber(String accountNumber);
    boolean existsByEmail(String email);
    boolean existsById(UUID id);

    UUID id(UUID id);
}
