package com.hb.core.entity;

public class Property extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2299775829083584745L;
	private String name;
	private String desc;
	private String value;
	
	
	public Property() {
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}


}