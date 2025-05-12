package com.bnr.bank.services;

import com.bnr.bank.dto.CustomerDTO;
import com.bnr.bank.dto.RegisterDTO;
import com.bnr.bank.enums.ERole;
import com.bnr.bank.models.Customer;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CustomerService {
    RegisterDTO registerUser(RegisterDTO customer, Set<ERole> role);
    String loginUser(String email, String password);
    Customer getCustomerById(UUID id);
    Customer getCustomerByAccount(String account);
    CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO);
    void deleteCustomer(UUID id);
    List<Customer> getAllCustomers();
    boolean hasRole(String role);

}
