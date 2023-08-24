package com.assessment.storeAPI;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.model.BillResponse;
import com.assessment.storeAPI.enums.CustomerType;
import com.assessment.storeAPI.services.DiscountService;
import com.assessment.storeAPI.services.impl.DiscountServiceImpl;
import com.assessment.storeAPI.strategy.DiscountContext;
import com.assessment.storeAPI.strategy.impl.SavingsDiscountStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DiscountServiceImplTest {

    @Mock
    private SavingsDiscountStrategy savingsDiscountStrategy;

    @Mock
    private DiscountContext discountContext;

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        discountService = new DiscountServiceImpl(savingsDiscountStrategy, discountContext);
    }
    @Test
    void calculateDiscounts_withRegularCustomerType() {
        Bill bill = new Bill("456", "RegularCustomerBill", 100.0, CustomerType.REGULAR);
        mockDiscountContext(10.0, "Loyal Customer Discount");
        mockSavingsDiscountStrategy(5.0);
        BillResponse response = discountService.calculateDiscounts(bill);

        Map<String, Double> discounts = new HashMap<String, Double>() {{
            put("Loyal Customer Discount", 10.0);
            put("Cumulative Savings Discount", 5.0);
        }};

        assertBillResponse(response,
                new BillResponse(100.0,discounts, 85.0));
        verify(savingsDiscountStrategy, times(1)).calculateDiscount(anyDouble());
    }

    @Test
    void calculateDiscounts_withNullCustomerTypeAndZeroAmount() {
        Bill bill = new Bill("789", "NullCustomerTypeAndZeroAmountBill", 0.0, null);
        mockSavingsDiscountStrategy(5.0);
        BillResponse response = discountService.calculateDiscounts(bill);

        assertBillResponse(response,new BillResponse(0.0,Collections.EMPTY_MAP,0.0));
        verify(discountContext, never()).calculateDiscount(any(Bill.class));
    }

    @Test
    void calculateDiscounts_withNullCustomerType() {
        Bill bill = new Bill("123","DiscountsWithNullCustomerTypeBill",200.0, null);
        mockSavingsDiscountStrategy(10.0);
        BillResponse response = discountService.calculateDiscounts(bill);

        assertBillResponse(response,
                new BillResponse(200.0,
                        Collections.singletonMap("Cumulative Savings Discount", 10.0), 190.0));
        verify(discountContext, never()).calculateDiscount(any(Bill.class));
    }

    @Test
    void calculateDiscounts_withZeroAmount() {
        Bill bill = new Bill("123","calculateDiscountsWithZeroAmountBill",0.0, CustomerType.EMPLOYEE);
        mockDiscountContext(0.0, "Employee Discount");
        mockSavingsDiscountStrategy(0.0);

        BillResponse response = discountService.calculateDiscounts(bill);
        assertBillResponse(response,
                new BillResponse(0.0,
                        Collections.EMPTY_MAP, 0.0));
        verify(savingsDiscountStrategy, never()).calculateDiscount(anyDouble());
    }

    private void mockDiscountContext(double discount, String discountId) {
        when(discountContext.calculateDiscount(any(Bill.class))).thenReturn(discount);
        when(discountContext.getDiscountId(any(Bill.class))).thenReturn(discountId);
    }

    private void mockSavingsDiscountStrategy(double discount) {
        when(savingsDiscountStrategy.calculateDiscount(anyDouble())).thenReturn(discount);
        when(savingsDiscountStrategy.getDiscountId()).thenReturn("Cumulative Savings Discount");
    }

    private void assertBillResponse(BillResponse real, BillResponse expected) {

        assertEquals(expected.getOriginalAmount(), real.getOriginalAmount());
        assertEquals(real.getAppliedDiscounts().size(), real.getAppliedDiscounts().size());
        assertTrue(expected.getAppliedDiscounts().entrySet().containsAll(real.getAppliedDiscounts().entrySet()));
        assertEquals(real.getNewAmount(), real.getNewAmount());
    }
}
