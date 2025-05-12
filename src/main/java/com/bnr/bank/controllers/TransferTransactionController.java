package com.bnr.bank.controllers;

import com.bnr.bank.models.TransferTransaction;
import com.bnr.bank.services.TransferTransactionService;
import com.bnr.bank.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfer")
public class TransferTransactionController {

    private final TransferTransactionService transferService;
    private final JwtTokenUtil jwtTokenUtil;

    public TransferTransactionController(TransferTransactionService transferService, JwtTokenUtil jwtTokenUtil) {
        this.transferService = transferService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<?> transferMoney(@RequestBody Map<String, Object> requestBody,
                                           HttpServletRequest request) {
        try {
            // Step 1: Get sender from JWT
            UUID senderId = jwtTokenUtil.getCurrentUserId(request);

            // Step 2: Validate and extract inputs
            if (!requestBody.containsKey("receiverId") || !requestBody.containsKey("amount")) {
                return ResponseEntity.badRequest().body("Missing receiverId or amount");
            }

            UUID receiverId = UUID.fromString(requestBody.get("receiverId").toString());
            Double amount = Double.parseDouble(requestBody.get("amount").toString());

            // Step 3: Call the service
            TransferTransaction transaction = transferService.transferMoney(senderId, receiverId, amount);

            return ResponseEntity.ok(transaction);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID or amount format: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Transfer failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal error: " + e.getMessage());
        }
    }
}
