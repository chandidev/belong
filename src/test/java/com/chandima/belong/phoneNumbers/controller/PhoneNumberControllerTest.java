package com.chandima.belong.phoneNumbers.controller;


import com.chandima.belong.phoneNumbers.Application;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneNumberControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetAllPhoneNumbers() {
        given()
                .log().all()
                .when()
                .get("/api/phoneNumbers")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("phoneNumber", hasItems("0311110001", "0311110002","0311110003", "0311110007", "0311110002"))
                ;
    }

    @Test
    public void testGetCustomerPhoneNumbers() {
        given()
                .log().all()
                .when()
                .get("/api/customer/1/phoneNumbers")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("phoneNumber", hasItems("0311110001", "0311110002","0311110003"))
        ;
    }

    @Test
    public void testActivateSuccess() {
        given()
                .log().all()
                .when()
                .put("/api/phoneNumbers/0311110001/activate")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("result", containsString("success"));
        ;
    }

    @Test
    public void testActivateNotFound() {
        given()
                .log().all()
                .when()
                .put("/api/phoneNumbers/00000/activate")
                .then()
                .log().all()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("message", containsString("phone number not found"));
        ;
    }
}
