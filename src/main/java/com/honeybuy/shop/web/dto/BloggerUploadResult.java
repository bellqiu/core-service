package com.honeybuy.shop.web.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.hb.core.entity.Blogger;

@JsonAutoDetect
public class BloggerUploadResult {
	
	private boolean success;
	private String message = "faild";
	private Blogger blogger;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Blogger getBlogger() {
		return blogger;
	}
	public void setBlogger(Blogger blogger) {
		this.blogger = blogger;
	}
	
	
}
