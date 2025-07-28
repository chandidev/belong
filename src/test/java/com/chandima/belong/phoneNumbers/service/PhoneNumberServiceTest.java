package com.chandima.belong.phoneNumbers.service;

import com.chandima.belong.phoneNumbers.exception.ClientException;
import com.chandima.belong.phoneNumbers.model.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberServiceTest {

    private PhoneNumberService phoneNumberService = new PhoneNumberService();

    @Test
    public void testGetAllPhoneNumbers() {
        List<PhoneNumber> allPhoneNumbers = phoneNumberService.getAllPhoneNumbers();
        assertEquals(0, allPhoneNumbers.size());

        populatePhoneNumbers();

        assertEquals(6, allPhoneNumbers.size());
        assertEquals("0311110001", allPhoneNumbers.get(0).getPhoneNumber());
    }

    @Test
    public void testGetCustomerPhoneNumbers_whenExists_returnList() {
        populatePhoneNumbers();
        List<PhoneNumber> allPhoneNumbers = phoneNumberService.getPhoneNumbers(1);

        assertEquals(3, allPhoneNumbers.size());
        assertTrue(allPhoneNumbers.stream()
                .anyMatch(phoneNumber -> phoneNumber.getPhoneNumber().equals("0311110001")));
    }

    @Test
    public void testGetCustomerPhoneNumbers_whenNotExists_returnEmptyList() {
        populatePhoneNumbers();
        List<PhoneNumber> allPhoneNumbers = phoneNumberService.getPhoneNumbers(6);

        assertEquals(0, allPhoneNumbers.size());
    }

    @Test
    public void activate_whenExists_Activates() {
        populatePhoneNumbers();
        //pick an inactive phone number
        PhoneNumber inActivePhoneNumber = phoneNumberService.getPhoneNumbers(1).stream().filter(phoneNumber -> !phoneNumber.isActive()).findFirst().get();
        //verify
        assertNotNull(inActivePhoneNumber);
        //activate
        phoneNumberService.activate(inActivePhoneNumber.getPhoneNumber());
        //retrieve same phone number
        PhoneNumber activatedPhoneNumber = phoneNumberService.getPhoneNumbers(1).stream()
                .filter(phoneNumber -> phoneNumber.getPhoneNumber().equals(inActivePhoneNumber.getPhoneNumber()))
                .findFirst().get();
        //verify activated.
        assertTrue(activatedPhoneNumber.isActive());

    }

    @Test
    public void activate_whenNotExists_ClientException() {
        populatePhoneNumbers();
        //activate
        ClientException clientException = assertThrows(ClientException.class, () -> phoneNumberService.activate("notExisting"));
        assertEquals("phone number not found", clientException.getMessage());
    }


    private void populatePhoneNumbers() {
        List<PhoneNumber> phoneNumberList = (List<PhoneNumber>) ReflectionTestUtils.getField(phoneNumberService, "phoneNumberList");

        phoneNumberList.add(PhoneNumber.builder().customerId(1).phoneNumber("0311110001").active(false).build());
        phoneNumberList.add(PhoneNumber.builder().customerId(1).phoneNumber("0311110002").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(1).phoneNumber("0311110003").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(2).phoneNumber("0311110005").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(2).phoneNumber("0311110006").build());
        phoneNumberList.add(PhoneNumber.builder().customerId(3).phoneNumber("0311110007").build());

    }
}
