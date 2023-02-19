package com.email.app.services;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.email.app.EmailRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServiceTest {
	
	EmailService service = new EmailService();
	
	
	@Test
	public void testSendEmailWithoutRecipients() throws Exception {
	    EmailRequest request = new EmailRequest();
	    request.setSubject("Test email");
	    request.setBody("Hello, this is a test email!");
	    ResponseEntity<?> response = service.sendEmail(request, false);
	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	    assertEquals("Recipients field is required.", response.getBody());
	}
	
	
	@Test
	public void testSendEmailWithRecipients() throws Exception {
	    EmailRequest request = new EmailRequest();
	    request.setTo(Arrays.asList("recipient1@example.com", "recipient2@example.com"));
	    request.setSubject("Test email");
	    request.setBody("Hello, this is a test email!");
	    ResponseEntity<?> response = service.sendEmail(request,false);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
