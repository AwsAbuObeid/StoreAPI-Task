package com.assessment.storeAPI.deprecated;

import com.assessment.storeAPI.controller.BillsController;
import com.assessment.storeAPI.enums.ErrorType;
import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.model.BillResponse;
import com.assessment.storeAPI.services.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BillsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DiscountService discountService;

    private BillsController billsController;

//    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        billsController = new BillsController(discountService);
        mockMvc = MockMvcBuilders.standaloneSetup(billsController).build();
    }

//    @Test
    void calculate_withValidBill() throws Exception {
        Bill bill = new Bill();
        bill.setAmount(100.0);

        BillResponse billResponse = new BillResponse();
        billResponse.setOriginalAmount(100.0);
        billResponse.setNewAmount(90.0);

        when(discountService.calculateDiscounts(any(Bill.class))).thenReturn(billResponse);

        performPost("/api/v1/bills/payable", "{\"amount\": 100.0}")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.originalAmount").value(100.0))
            .andExpect(jsonPath("$.newAmount").value(90.0));

        verify(discountService, times(1)).calculateDiscounts(any(Bill.class));
    }

//    @Test
    void handleRequestException_withIllegalArgumentException() throws Exception {
        performPost("/api/v1/bills/payable", "{\"amount\": -100.0}")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorType").value(ErrorType.VALIDATION_ERROR.toString()))
            .andExpect(jsonPath("$.errorMessage").value("Amount cannot be less than zero!"));

        verify(discountService, never()).calculateDiscounts(any(Bill.class));
    }

//    @Test
    void handleRequestException_withHttpMessageNotReadableException() throws Exception {
        performPost("/api/v1/bills/payable", "invalidJsonContent")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorType").value(ErrorType.BAD_REQUEST.toString()));

        verify(discountService, never()).calculateDiscounts(any(Bill.class));
    }

//    @Test
    void handleRequestException_withUnexpectedException() throws Exception {
        performPost("/api/v1/bills/payable", "{\"amount\": 100.0}")
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorType").value(ErrorType.VALIDATION_ERROR.toString()))
            .andExpect(jsonPath("$.errorMessage").value("Name must be longer than 10 characters!"));

        verify(discountService, never()).calculateDiscounts(any(Bill.class));
    }

    private ResultActions performPost(String uri, String content) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content)
            .accept(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder);
    }
}
