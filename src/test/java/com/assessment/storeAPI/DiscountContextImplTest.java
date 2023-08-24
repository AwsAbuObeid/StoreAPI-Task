package com.assessment.storeAPI;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.enums.CustomerType;
import com.assessment.storeAPI.strategy.DiscountStrategy;
import com.assessment.storeAPI.strategy.impl.DiscountContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountContextImplTest {

    @Mock
    private DiscountStrategy employeeDiscountStrategy;

    @Mock
    private DiscountStrategy regularDiscountStrategy;

    private DiscountContextImpl discountContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<String, DiscountStrategy> strategies = new HashMap<>();
        strategies.put("employeeDiscountStrategy",employeeDiscountStrategy);
        strategies.put("regularDiscountStrategy",regularDiscountStrategy);
        discountContext = new DiscountContextImpl(strategies);
    }

    @Test
    void calculateDiscount_withEmployeeCustomerType() {
        Bill bill = new Bill("123", "TestBillName", 100.0, CustomerType.EMPLOYEE);
        when(employeeDiscountStrategy.calculateDiscount(anyDouble())).thenReturn(30.0);
        
        double result = discountContext.calculateDiscount(bill);
        
        assertEquals(30.0, result);
        verify(employeeDiscountStrategy, times(1)).calculateDiscount(anyDouble());
        verify(regularDiscountStrategy, never()).calculateDiscount(anyDouble());
    }

    @Test
    void calculateDiscount_withRegularCustomerType() {
        Bill bill = new Bill("456", "TestBillName", 150.0, CustomerType.REGULAR);
        when(regularDiscountStrategy.calculateDiscount(anyDouble())).thenReturn(15.0);
        
        double result = discountContext.calculateDiscount(bill);
        
        assertEquals(15.0, result);
        verify(regularDiscountStrategy, times(1)).calculateDiscount(anyDouble());
        verify(employeeDiscountStrategy, never()).calculateDiscount(anyDouble());
    }

    @Test
    void calculateDiscount_withNullBill() {
        Bill bill = new Bill("789", "TestBillName", 200.0, null);
        
        assertThrows(IllegalArgumentException.class, () -> discountContext.calculateDiscount(null));
        
        verify(employeeDiscountStrategy, never()).calculateDiscount(anyDouble());
        verify(regularDiscountStrategy, never()).calculateDiscount(anyDouble());
    }
    @Test
    void getDiscountId_withNullCustomerType() {
        Bill bill = new Bill("123", "TestBillName", 100.0, null);
        assertNull(discountContext.getDiscountId(bill));
    }

}
