package com.assessment.storeAPI;

import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.util.BillUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillUtilTest {

    @Test
    void validateBill_withValidBill() {
        Bill validBill = new Bill();
        validBill.setAmount(100.0);
        validBill.setName("ValidBillName");

        assertDoesNotThrow(() -> BillUtil.validateBill(validBill));
    }

    @Test
    void validateBill_withNullBill() {
        assertThrows(IllegalArgumentException.class, () -> BillUtil.validateBill(null));
    }

    @Test
    void validateBill_withNegativeAmount() {
        Bill invalidBill = new Bill();
        invalidBill.setAmount(-50.0);
        invalidBill.setName("InvalidBillName");

        assertThrows(IllegalArgumentException.class, () -> BillUtil.validateBill(invalidBill));
    }

    @Test
    void validateBill_withShortName() {
        Bill invalidBill = new Bill();
        invalidBill.setAmount(200.0);
        invalidBill.setName("Short");

        assertThrows(IllegalArgumentException.class, () -> BillUtil.validateBill(invalidBill));
    }

    @Test
    void validateBill_withNullName() {
        Bill invalidBill = new Bill();
        invalidBill.setAmount(300.0);
        invalidBill.setName(null);

        assertThrows(IllegalArgumentException.class, () -> BillUtil.validateBill(invalidBill));
    }

    @Test
    void validateBill_withValidNullName() {
        Bill validBill = new Bill();
        validBill.setAmount(400.0);
        validBill.setName("ValidNullName");

        assertDoesNotThrow(() -> BillUtil.validateBill(validBill));
    }

}
