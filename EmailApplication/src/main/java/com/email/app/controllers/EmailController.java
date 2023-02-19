package com.email.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.email.app.EmailRequest;
import com.email.app.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping("/send")
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailrequest,
			@RequestParam(required = false, defaultValue = "false") boolean enrich) {

		return emailService.sendEmail(emailrequest, enrich);

	}
}
