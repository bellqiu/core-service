package com.hb.core.entity;

import java.util.List;

public class Option extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8504143946515106133L;
	
	private String desc;
	
	private List<OptionItem> items;
	
	private String defaultValue;
	
	private String name;
	
	private Type type = Type.TEXT;
	
	public static enum Type{
		TEXT,
		SINGLE_TEXT_LIST,
		MULTI_TEXT_LIST,
		SINGLE_ICON_LIST,
		MULTI_ICON_LIST
	}
	
	public Option() {
		
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<OptionItem> getItems() {
		return items;
	}

	public void setItems(List<OptionItem> items) {
		this.items = items;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
