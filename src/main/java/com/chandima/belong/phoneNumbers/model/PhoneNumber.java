package com.chandima.belong.phoneNumbers.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneNumber {
    private String phoneNumber;
    private Integer customerId;
    private boolean active;


}
