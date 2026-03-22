package com.swapnil.bookstore.app.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void pay(double amount) {
        // Simulated payment success
        // Replace with Stripe / Razorpay SDK
        if (amount <= 0)
            throw new RuntimeException("Invalid payment amount");
    }
}

