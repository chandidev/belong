package com.chandima.belong.phoneNumbers.controller;


import com.chandima.belong.phoneNumbers.model.ActivatePhoneRequest;
import com.chandima.belong.phoneNumbers.model.PhoneNumber;
import com.chandima.belong.phoneNumbers.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @GetMapping("/phoneNumbers")
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumberService.getAllPhoneNumbers();
    }

    @GetMapping("/customer/{customerId}/phoneNumbers")
    public List<PhoneNumber> getPhoneNumbers(@PathVariable Integer customerId) {
        return phoneNumberService.getPhoneNumbers(customerId);
    }

    @PostMapping("/customer/{customerId}/activate")
    public void activatePhone(@PathVariable Integer customerId,  @RequestBody ActivatePhoneRequest activatePhoneRequest) {
        this.phoneNumberService.activate(activatePhoneRequest.getPhoneNumber());

    }

}
