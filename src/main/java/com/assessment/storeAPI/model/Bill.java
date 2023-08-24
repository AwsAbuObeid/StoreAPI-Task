package com.assessment.storeAPI.model;

import com.assessment.storeAPI.enums.CustomerType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private String id;
    private String name;
    private double amount;
    private CustomerType customerType;

}
