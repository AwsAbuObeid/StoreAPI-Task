package com.assessment.storeAPI.strategy.impl;

import com.assessment.storeAPI.strategy.DiscountStrategy;
import com.assessment.storeAPI.util.BillUtil;
import org.springframework.stereotype.Service;

@Service
public class RegularDiscountStrategy implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.1;

    @Override
    public double calculateDiscount(double amount) {
        return amount * DISCOUNT_RATE;
    }

    @Override
    public String getDiscountId() {
        return "Loyal Customer Discount";
    }
}