package com.honeybuy.shop.web.dto;

import java.util.HashMap;
import java.util.Map;

public class SupportEntity {
	
	private String email;
	
	private String subject;
	
	private String message;
	
	private String orderNumber;
	
	private String phoneNumber;
	
	public SupportEntity() {}

	public SupportEntity(String email, String subject, String message) {
		this.email = email;
		this.subject = subject;
		this.message = message;
	}
	
	public SupportEntity(String email, String subject, String message, String orderNumber, String phoneNumber) {
		this.email = email;
		this.subject = subject;
		this.message = message;
		this.orderNumber = orderNumber;
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("email", email);
		content.put("subject", subject);
		content.put("message", message);
		content.put("orderNumber", orderNumber);
		content.put("phoneNumber", phoneNumber);
		return content;
	}

}
