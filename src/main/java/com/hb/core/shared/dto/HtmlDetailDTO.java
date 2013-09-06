package com.hb.core.shared.dto;


import javax.validation.constraints.NotNull;

public class HtmlDetailDTO {
	
	private long id;
	
	@NotNull
	private String name;
	
	private String content;
	
	
	public HtmlDetailDTO() {
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


}
