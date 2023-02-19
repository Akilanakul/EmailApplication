package com.email.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.email.app.EmailRequest;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmailService {
	@Value("${app.sendgrid.key}")
	private String key;

	@Value("${app.sendgrid.filter}")
	private Boolean filter;
	
	@Value("${app.sendgrid.filter.emaildomain}")
	private String emaildomainToFilter;
	
	@Value("${app.sendgrid.email.from}")
	private String fromEmailId;

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest, boolean enrich) {
		SendGrid sg = new SendGrid(key);
		Mail mail = buildMail(emailRequest, enrich);

		try {
			// Add multiple recipients if required
			if (emailRequest.getTo() == null || emailRequest.getTo().isEmpty()) {
				return ResponseEntity.badRequest().body("Recipients field is required.");
			} else {
				//filter all non @rakenapp.com mails
				filterNonNativeMails(emailRequest);
				if (emailRequest.getTo() != null && !emailRequest.getTo().isEmpty()) {
					Personalization personalization = new Personalization();
					for (String recipient : emailRequest.getTo()) {
						personalization.addTo(new Email(recipient));
					}
					mail.addPersonalization(personalization);
				}

			}

			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			return ResponseEntity.ok(response.getBody());
		} catch (IOException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error sending email: " + ex.getMessage());
		}
	}

	private void filterNonNativeMails(EmailRequest emailRequest) {
		if (filter) {
			// Filter out emails targeted to non rakenapp.com domains
			List<String> invalidEmails = emailRequest.getTo().stream()
					.filter(email -> !email.endsWith(emaildomainToFilter)).collect(Collectors.toList());

			// Log the invalid emails
			if (!invalidEmails.isEmpty()) {
				logger.warn("Ignoring emails targeted to non rakenapp.com domains: {}", invalidEmails);
			}

			// Remove the invalid emails from the list of recipients
			emailRequest.getTo().removeAll(invalidEmails);
		}
	}

	private Mail buildMail(EmailRequest emailRequest, boolean enrich) {
		Email from = new Email(fromEmailId);
		String subject = emailRequest.getSubject();

		// check if enrich enabled
		Content content = new Content("text/plain",
				enrich ? (emailRequest.getBody() + "current weather") : emailRequest.getBody());
		Mail mail = new Mail();
		mail.setFrom(from);
		mail.setSubject(subject);
		mail.addContent(content);
		return mail;
	}
	
}
