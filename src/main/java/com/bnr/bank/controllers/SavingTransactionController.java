package com.bnr.bank.controllers;

import com.bnr.bank.models.SavingTransaction;
import com.bnr.bank.services.SavingTransactionService;
import com.bnr.bank.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/saving")
public class SavingTransactionController {

    private SavingTransactionService savingService;
    private JwtTokenUtil jwtTokenUtil;

    public SavingTransactionController(SavingTransactionService savingService, JwtTokenUtil jwtTokenUtil) {
        this.savingService = savingService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<?> saveMoney(@PathVariable UUID customerId,
                                       @RequestBody Map<String, Double> requestBody,
                                       HttpServletRequest request) {

        UUID userId = jwtTokenUtil.getCurrentUserId(request);
        if (!userId.equals(customerId)) {
            return ResponseEntity.status(403).body("Forbidden: You can only update your own account");
        }
        Double amount = requestBody.get("amount");
        return ResponseEntity.ok(savingService.saveMoney(customerId, amount));
    }


}

