package com.hb.core.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
@JsonAutoDetect
public class UserDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4471936854609700424L;
	private long id;
	@NotNull
	private String email;
	private String password;
	private boolean enabled;
	private final List<String> roles = new ArrayList<String>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
