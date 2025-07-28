package com.chandima.belong.phoneNumbers.service;

import com.chandima.belong.phoneNumbers.exception.NotFoundException;
import com.chandima.belong.phoneNumbers.model.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneNumberService {

    List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();

    /**
     * initialize phone numbers. as per assignment details. in a production scenario data will be fedtechd from database.
     **/
    @PostConstruct
    public void init() {
        phoneNumberList.add(PhoneNumber.builder().customerId(1).phoneNumber("0311110001").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(1).phoneNumber("0311110002").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(1).phoneNumber("0311110003").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(2).phoneNumber("0311110005").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(2).phoneNumber("0311110006").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(3).phoneNumber("0311110007").build());
    }


    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumberList;
    }

    public List<PhoneNumber> getPhoneNumbers(Integer customerId) {
        return phoneNumberList.stream().filter(phoneNumber -> phoneNumber.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    public void activate(String phoneNumber) {
        phoneNumberList.stream()
                .filter(number -> number.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .ifPresentOrElse(number -> number.setActive(true),
                        () -> {
                            throw new NotFoundException("phone number not found");
                        });

    }


}
