package com.assessment.storeAPI.services.impl;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.model.BillResponse;
import com.assessment.storeAPI.services.DiscountService;
import com.assessment.storeAPI.strategy.DiscountContext;
import com.assessment.storeAPI.strategy.impl.SavingsDiscountStrategy;
import com.assessment.storeAPI.util.BillUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final SavingsDiscountStrategy savingsDiscountStrategy;
    private final DiscountContext discountContext;

    public DiscountServiceImpl(SavingsDiscountStrategy savingsDiscountStrategy, DiscountContext discountContext) {
        this.savingsDiscountStrategy = savingsDiscountStrategy;
        this.discountContext = discountContext;
    }
    @Override
    public BillResponse calculateDiscounts(Bill bill) {
        BillUtil.validateBill(bill);

        Map<String,Double> appliedDiscounts = new HashMap<>();
        BillResponse billResponse = new BillResponse();

        billResponse.setAppliedDiscounts(appliedDiscounts);
        billResponse.setOriginalAmount(bill.getAmount());

        if(bill.getAmount()==0) {
            billResponse.setNewAmount(0);
            return billResponse;
        }

        double typeDiscount=0.0;
        if(bill.getCustomerType()!=null) {
            typeDiscount = discountContext.calculateDiscount(bill);
            appliedDiscounts.put(discountContext.getDiscountId(bill), round(typeDiscount));
        }
        //Calculate bill amount discount using strategy
        double savings= savingsDiscountStrategy.calculateDiscount(bill.getAmount());
        appliedDiscounts.put(savingsDiscountStrategy.getDiscountId(),round(savings));

        // Calculate new payable amount after applying discounts
        double newAmount = bill.getAmount() - (typeDiscount+savings);

        billResponse.setNewAmount(round(newAmount));

        return billResponse;
    }
    private double round(double num){
        return Math.round(num * 1000.0) / 1000.0;
    }

}
