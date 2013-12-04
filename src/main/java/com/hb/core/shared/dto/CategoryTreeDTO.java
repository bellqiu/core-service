package com.hb.core.shared.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.hb.core.entity.Category;
@JsonIgnoreProperties(ignoreUnknown = true, value={"parentId"})
@JsonAutoDetect
public class CategoryTreeDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1028413191935287435L;
	private long id;
	private String name;
	private String displayName;
	private Category.Type type = Category.Type.NAVIGATION;
	private String iconUrl;
	private String url;
	
	private long parentId;
	
	private boolean leaf;
	
	public CategoryTreeDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Category.Type getType() {
		return type;
	}

	public void setType(Category.Type type) {
		this.type = type;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
}
