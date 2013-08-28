package com.hb.core.shared.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserDTO {
	@NotBlank
	private String uid;
	@Email
	@NotNull
	private String email;
	private String password;
	private boolean enabled;
	private final List<String> roles = new ArrayList<String>();
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getRoles() {
		return roles;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
