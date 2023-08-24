package com.assessment.storeAPI.strategy.impl;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.strategy.DiscountStrategy;
import org.springframework.stereotype.Service;

@Service
public class SavingsDiscountStrategy implements DiscountStrategy {
    private static final double DISCOUNT_AMOUNT = 5;
    private static final double BILL_AMOUNT_THRESHOLD = 100;

    @Override
    public double calculateDiscount(double amount) {
        return (int) (amount / BILL_AMOUNT_THRESHOLD) * DISCOUNT_AMOUNT;
    }

    @Override
    public String getDiscountId() {
        return "Cumulative Savings Discount";
    }
}