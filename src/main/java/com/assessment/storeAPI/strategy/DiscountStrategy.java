package com.assessment.storeAPI.strategy;

import com.assessment.storeAPI.model.Bill;

public interface DiscountStrategy {
    double calculateDiscount(double amount);

    String getDiscountId();

}
