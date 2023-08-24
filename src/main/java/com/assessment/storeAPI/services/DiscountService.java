package com.assessment.storeAPI.services;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.model.BillResponse;

public interface DiscountService {
    BillResponse calculateDiscounts(Bill bill);
}
