package com.assessment.storeAPI.model;

import com.assessment.storeAPI.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private ErrorType errorType;
    private String errorMessage;
    private String timeStamp;
}
