package com.assessment.storeAPI.util;

import com.assessment.storeAPI.model.Bill;

public class BillUtil {
    public static void validateBill(Bill bill){
        if (bill == null) throw new IllegalArgumentException("bill cannot be null!");
        if( bill.getAmount() < 0 )  throw new IllegalArgumentException("Amount cannot be less than zero!");
        if (bill.getName()==null||bill.getName().length()<10) throw new IllegalArgumentException("Name must be longer than 10 characters!");
    }
}
