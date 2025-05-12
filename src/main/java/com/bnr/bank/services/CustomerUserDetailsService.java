package com.bnr.bank.services;


import com.bnr.bank.models.Customer;
import com.bnr.bank.repositories.CustomerRepo;
import com.bnr.bank.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    public CustomerUserDetailsService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email));

        return UserPrincipal.create(customer);
    }

    @Transactional
    public UserDetails loadUserById(UUID id) {
        Customer customer = customerRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );

        return UserPrincipal.create(customer);
    }
}
