package com.assessment.storeAPI;

import com.assessment.storeAPI.controller.BillsController;
import com.assessment.storeAPI.enums.ErrorType;
import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.model.BillResponse;
import com.assessment.storeAPI.model.ValidationErrorResponse;
import com.assessment.storeAPI.services.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillsControllerTest {

    @Mock
    private DiscountService discountService;

    private BillsController billsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        billsController = new BillsController(discountService);
    }

    @Test
    void calculate_withValidBill() {
        Bill bill = new Bill();
        when(discountService.calculateDiscounts(bill)).thenReturn(new BillResponse());

        BillResponse response = billsController.calculate(bill);

        assertNotNull(response);
        verify(discountService, times(1)).calculateDiscounts(bill);
    }

    @Test
    void handleRequestException_withIllegalArgumentException() {
        String errorMessage = "Invalid argument";
        Exception ex = new IllegalArgumentException(errorMessage);

        ValidationErrorResponse errorResponse = billsController.handleRequestException(ex);

        assertNotNull(errorResponse);
        assertEquals(ErrorType.VALIDATION_ERROR, errorResponse.getErrorType());
        assertEquals(errorMessage, errorResponse.getErrorMessage());
        assertNotNull(errorResponse.getTimeStamp());
    }

    @Test
    void handleRequestException_withHttpMessageNotReadableException() {
        String errorMessage = "Malformed JSON";
        Exception ex = new HttpMessageNotReadableException(errorMessage);

        ValidationErrorResponse errorResponse = billsController.handleRequestException(ex);

        assertNotNull(errorResponse);
        assertEquals(ErrorType.BAD_REQUEST, errorResponse.getErrorType());
        assertEquals(errorMessage, errorResponse.getErrorMessage());
        assertNotNull(errorResponse.getTimeStamp());
    }

    @Test
    void handleRequestException_withOtherException() {
        Exception ex = new NullPointerException();

        ValidationErrorResponse errorResponse = billsController.handleRequestException(ex);

        assertNotNull(errorResponse);
        assertEquals(ErrorType.INTERNAL_SERVER_ERROR, errorResponse.getErrorType());
        assertEquals("An unexpected error occurred while processing your request.", errorResponse.getErrorMessage());
        assertNotNull(errorResponse.getTimeStamp());
    }

}
