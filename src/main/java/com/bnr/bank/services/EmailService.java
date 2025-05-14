package com.bnr.bank.services;

public interface EmailService {
    void sendTransactionConfirmation(String toEmail, String subject, String body);
}
