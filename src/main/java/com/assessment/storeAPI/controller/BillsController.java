package com.assessment.storeAPI.controller;

import com.assessment.storeAPI.enums.ErrorType;
import com.assessment.storeAPI.model.Bill;
import com.assessment.storeAPI.model.BillResponse;
import com.assessment.storeAPI.model.ValidationErrorResponse;
import com.assessment.storeAPI.services.DiscountService;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;


@RestController
@RequestMapping("/api/v1/bills")
public class BillsController {

    private final DiscountService discountService;
    public BillsController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/payable",produces = "application/json")
    public BillResponse calculate(@RequestBody Bill bill) {
        return discountService.calculateDiscounts(bill);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ValidationErrorResponse handleRequestException(Exception ex) {
        ErrorType errorType;
        String message;
        if(ex instanceof IllegalArgumentException) {
            errorType = ErrorType.VALIDATION_ERROR; message= ex.getMessage();
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            errorType = ErrorType.BAD_REQUEST;      message=ex.getMessage();
        }
        else {
            errorType = ErrorType.INTERNAL_SERVER_ERROR;
            message="An unexpected error occurred while processing your request." ;
        }

        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setErrorType(errorType);
        validationErrorResponse.setErrorMessage(message);
        validationErrorResponse.setTimeStamp(Timestamp.from(Instant.now()).toString());
        return validationErrorResponse;
    }

}
