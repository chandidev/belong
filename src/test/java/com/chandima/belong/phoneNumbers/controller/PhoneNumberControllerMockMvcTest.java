package com.chandima.belong.phoneNumbers.controller;

import com.chandima.belong.phoneNumbers.exception.NotFoundException;
import com.chandima.belong.phoneNumbers.model.PhoneNumber;
import com.chandima.belong.phoneNumbers.service.PhoneNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhoneNumberController.class)
public class PhoneNumberControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @Test
    public void testGetAllPhoneNumbers() throws Exception {
        List<PhoneNumber> mockList = List.of(
                PhoneNumber.builder().customerId(1).phoneNumber("0311110000").build(),
                PhoneNumber.builder().customerId(1).phoneNumber("0311110001").build(),
                PhoneNumber.builder().customerId(2).phoneNumber("0311110002").build(),
                PhoneNumber.builder().customerId(2).phoneNumber("0311110003").build()
        );
        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(mockList);

        mockMvc.perform(get("/api/phoneNumbers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].phoneNumber").value("0311110000"));
    }

    @Test
    public void testGetCustomerPhoneNumbers() throws Exception {
        List<PhoneNumber> mockList = List.of(
                PhoneNumber.builder().customerId(1).phoneNumber("0311110000").build(),
                PhoneNumber.builder().customerId(1).phoneNumber("0311110001").build()
        );
        when(phoneNumberService.getPhoneNumbers(1)).thenReturn(mockList);

        mockMvc.perform(get("/api/customer/1/phoneNumbers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].phoneNumber").value("0311110000"));
    }

    @Test
    public void testActivate_success() throws Exception {
        mockMvc.perform(put("/api/phoneNumbers/11111111/activate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", containsString("success")));

        verify(phoneNumberService, times(1)).activate("11111111");

    }

    @Test
    public void testActivate_notFound() throws Exception {
        doThrow(new NotFoundException("not found")).when(phoneNumberService).activate("11111111");

        mockMvc.perform(put("/api/phoneNumbers/11111111/activate"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("not found")));

    }

}
