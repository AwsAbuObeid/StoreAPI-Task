package com.assessment.storeAPI.strategy;

import com.assessment.storeAPI.model.Bill;

public interface DiscountContext {
    double calculateDiscount(Bill bill);
    String getDiscountId(Bill bill);

}
