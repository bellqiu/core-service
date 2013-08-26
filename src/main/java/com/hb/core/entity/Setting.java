package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


import com.fasterxml.jackson.annotation.JsonAutoDetect;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonAutoDetect

@NamedQueries(value=
	{
		@NamedQuery(name="QuerySettingByKeyAndType", query="select s from Setting as s where name=:key and type=:type"),
		@NamedQuery(name="countAllSetting", query="select count(s.id) from Setting as s"),
	})
public class Setting extends Component{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6830864436623220292L;
	@Column(name="value")
	private String value;
	
	@Column(name="name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
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
