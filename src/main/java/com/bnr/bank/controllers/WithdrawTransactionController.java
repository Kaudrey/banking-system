package com.bnr.bank.controllers;


import com.bnr.bank.models.WithdrawTransaction;
import com.bnr.bank.services.WithdrawTransactionService;
import com.bnr.bank.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawTransactionController {

    private WithdrawTransactionService withdrawService;
    private JwtTokenUtil jwtTokenUtil;

    public WithdrawTransactionController(WithdrawTransactionService withdrawService, JwtTokenUtil jwtTokenUtil) {
        this.withdrawService = withdrawService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<?> withdrawMoney(@PathVariable UUID customerId,
                                                         @RequestBody Map<String, Double> requestBody,
                                                         HttpServletRequest request) {

        UUID userId = jwtTokenUtil.getCurrentUserId(request);
        if (!userId.equals(customerId)) {
            return ResponseEntity.status(403).body("Forbidden: You can only update your own account");
        }
        Double amount = requestBody.get("amount");
        return ResponseEntity.ok(withdrawService.withdrawMoney(customerId, amount));
    }


}