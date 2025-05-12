package com.bnr.bank.controllers;

import com.bnr.bank.dto.CustomerDTO;
import com.bnr.bank.services.CustomerService;
import com.bnr.bank.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final JwtTokenUtil jwtTokenUtil;

    public CustomerController(CustomerService customerService, JwtTokenUtil jwtTokenUtil) {
        this.customerService = customerService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO, HttpServletRequest request) {
        if (!jwtTokenUtil.isLoggedIn(request)) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in");
        }

        UUID userId = jwtTokenUtil.getCurrentUserId(request);
        if (!userId.equals(id)) {
            return ResponseEntity.status(403).body("Forbidden: You can only update your own account");
        }

        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id, HttpServletRequest request) {
        if (!jwtTokenUtil.isLoggedIn(request)) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in");
        }

        UUID userId = jwtTokenUtil.getCurrentUserId(request);
        if (!userId.equals(id)) {
            return ResponseEntity.status(403).body("Forbidden: You can only delete your own account");
        }

        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
