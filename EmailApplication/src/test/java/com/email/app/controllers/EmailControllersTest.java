package com.email.app.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.email.app.EmailRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailControllersTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSendEmail() throws Exception {
        // Send an email using the REST API
    	ArrayList<String> reciepients=new ArrayList<String>();
    	reciepients.add("aj.akila90mail.com");
        EmailRequest request = new EmailRequest();
        request.setTo(reciepients);
        request.setSubject("Test email");
        request.setBody("This is a test email");
        ResponseEntity<Void> response = restTemplate.postForEntity("/email/send", request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}