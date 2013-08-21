package com.hb.core.entity;


public class Setting extends Component{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6830864436623220292L;
	private String value;
	private String name;
	
	private Type type = Type.STRING;
	
	public static enum Type{
		NUMBER,
		STRING,
		HTML
	}
	
	public Setting() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
	
}
