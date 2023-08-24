package com.assessment.storeAPI;

import com.assessment.storeAPI.strategy.DiscountStrategy;
import com.assessment.storeAPI.strategy.impl.EmployeeDiscountStrategy;
import com.assessment.storeAPI.strategy.impl.RegularDiscountStrategy;
import com.assessment.storeAPI.strategy.impl.SavingsDiscountStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountStrategyTest {

    @Test
    void employeeDiscountStrategy() {
        DiscountStrategy strategy = new EmployeeDiscountStrategy();
        double discount = strategy.calculateDiscount(100.0);
        assertEquals(30.0, discount);
        assertEquals("Employee Discount", strategy.getDiscountId());
    }

    @Test
    void regularDiscountStrategy() {
        DiscountStrategy strategy = new RegularDiscountStrategy();
        double discount = strategy.calculateDiscount(150.0);
        assertEquals(15.0, discount);
        assertEquals("Loyal Customer Discount", strategy.getDiscountId());
    }

    @Test
    void savingsDiscountStrategy() {
        DiscountStrategy strategy = new SavingsDiscountStrategy();
        double discount = strategy.calculateDiscount(200.0);
        assertEquals(10.0, discount);
        assertEquals("Cumulative Savings Discount", strategy.getDiscountId());
    }

}
