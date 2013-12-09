package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="POption")
public class Option extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8504143946515106133L;
	
	@Column(name="description_value")
	private String desc;
	
	@OneToMany(targetEntity=OptionItem.class,orphanRemoval=true, cascade={CascadeType.ALL})
	@JoinColumn(name="Option_id")
	private List<OptionItem> items = new ArrayList<OptionItem>();
	
	@Column(name="default_value")
	private String defaultValue;
	
	@Column(name="name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type = Type.TEXT;
	
	@Column(name="customize")
	private boolean customize;
	
	@Column(name="htmlKey")
	private String htmlKey;
	
	public static enum Type{
		TEXT,
		SINGLE_TEXT_LIST,
		MULTI_TEXT_LIST,
		SINGLE_ICON_LIST,
		MULTI_ICON_LIST,
		SINGLE_INPUT_CHECKBOX
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

	public void setCustomize(boolean customize) {
		this.customize = customize;
	}

	public boolean isCustomize() {
		return customize;
	}

	public void setHtmlKey(String htmlKey) {
		this.htmlKey = htmlKey;
	}

	public String getHtmlKey() {
		return htmlKey;
	}
	
}
