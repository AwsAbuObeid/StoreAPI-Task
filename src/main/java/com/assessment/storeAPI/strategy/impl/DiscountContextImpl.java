package com.assessment.storeAPI.strategy.impl;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.enums.CustomerType;
import com.assessment.storeAPI.strategy.DiscountContext;
import com.assessment.storeAPI.strategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountContextImpl implements DiscountContext {
    private final Map<CustomerType, DiscountStrategy> strategyMap;

    public DiscountContextImpl(Map<String, DiscountStrategy> strategies) {
        strategyMap = new HashMap<>();
        Arrays.stream(CustomerType.values())
                .forEach(type -> strategyMap.put(type, strategies.get(getBeanName(type.getDiscountClass()))));
    }
    @Override
    public double calculateDiscount(Bill bill) {
        BillUtil.validateBill(bill);
        if (bill.getCustomerType()==null) return 0.0;
        return strategyMap.get(bill.getCustomerType()).calculateDiscount(bill.getAmount());
    }

    @Override
    public String getDiscountId(Bill bill) {
        BillUtil.validateBill(bill);
        if (bill.getCustomerType()==null) return null;
        return strategyMap.get(bill.getCustomerType()).getDiscountId();
    }

    private String getBeanName(Class<?> clazz){
        String className = clazz.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

}
