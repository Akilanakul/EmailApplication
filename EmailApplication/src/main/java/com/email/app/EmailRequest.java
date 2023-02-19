package com.email.app;

import java.util.List;

public class EmailRequest {

	private String subject;
	private String body;
	private String cc;
	private List<String> to;

	public EmailRequest() {
	}

	public EmailRequest(List<String> to, String subject, String body, String cc) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

}
