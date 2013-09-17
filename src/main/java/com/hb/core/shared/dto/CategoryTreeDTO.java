package com.hb.core.shared.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.hb.core.entity.Category;
@JsonIgnoreProperties(ignoreUnknown = true, value={"parentId"})
public class CategoryTreeDTO {
	private long id;
	private String name;
	private String displayName;
	private Category.Type type = Category.Type.NAVIGATION;
	
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
