package com.hb.core.shared.dto;


import javax.validation.constraints.NotNull;

import com.hb.core.entity.Category.Type;

public class CategoryDetailDTO {
	
	private long id;
	
	private String displayName;
	
	private String iconUrl;
	
	private String url;
	
	private String relatedKeyword;

	private String pageTitle;
	
	private String marketContent;
	
	private String description;
	
	@NotNull
	private String name;
	
	private Type type = Type.NAVIGATION;
	
	private int priority;
	
	private long parentId;
	
	public CategoryDetailDTO() {
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getIconUrl() {
		return iconUrl;
	}


	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getRelatedKeyword() {
		return relatedKeyword;
	}


	public void setRelatedKeyword(String relatedKeyword) {
		this.relatedKeyword = relatedKeyword;
	}


	public String getPageTitle() {
		return pageTitle;
	}


	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}


	public String getMarketContent() {
		return marketContent;
	}


	public void setMarketContent(String marketContent) {
		this.marketContent = marketContent;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getParentId() {
		return parentId;
	}


	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

}
