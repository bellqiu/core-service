package com.honeybuy.shop.web.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.hb.core.entity.Image;

@JsonAutoDetect
public class ImageUploadResult {
	private boolean success;
	private String message = "faild";
	private Image image;
	
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
