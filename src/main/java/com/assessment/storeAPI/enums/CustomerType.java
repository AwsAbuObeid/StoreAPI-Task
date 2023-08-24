package com.assessment.storeAPI.enums;

import com.assessment.storeAPI.strategy.DiscountStrategy;
import com.assessment.storeAPI.strategy.impl.EmployeeDiscountStrategy;
import com.assessment.storeAPI.strategy.impl.RegularDiscountStrategy;

public enum CustomerType {
    EMPLOYEE(EmployeeDiscountStrategy.class),
    REGULAR(RegularDiscountStrategy.class);

    private final Class<? extends DiscountStrategy> discountClass;

    CustomerType(Class<? extends DiscountStrategy> discountClass) {
        this.discountClass = discountClass;
    }
    public Class<? extends DiscountStrategy> getDiscountClass() {
        return discountClass;
    }


}
