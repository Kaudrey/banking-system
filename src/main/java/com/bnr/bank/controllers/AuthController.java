package com.bnr.bank.controllers;

import com.bnr.bank.dto.LoginDTO;
import com.bnr.bank.dto.RegisterDTO;
import com.bnr.bank.enums.ERole;
import com.bnr.bank.models.Customer;
import com.bnr.bank.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final CustomerService customerService;

    AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try{
            Set<ERole> role = new HashSet<>();
            role.add(ERole.CUSTOMER); //Default role

            RegisterDTO registered = customerService.registerUser(registerDTO, role);
            return ResponseEntity.ok(registered);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

            String token = customerService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);

    }

}
