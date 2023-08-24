package com.assessment.storeAPI.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {
    private double originalAmount;
    private Map<String, Double> appliedDiscounts;
    private double newAmount;


}

