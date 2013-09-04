package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Property extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2299775829083584745L;
	@Column(name="name")
	private String name;
	@Column(name="desc_value")
	private String desc;
	@Column(name="value")
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