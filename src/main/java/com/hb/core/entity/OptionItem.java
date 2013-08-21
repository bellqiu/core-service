package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

public class OptionItem extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3876490399646625342L;
	private String value;
	private String iconUrl;
	private String displayName;
	private float priceChange;

	private List<Property> overrideProps = new ArrayList<Property>();

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public List<Property> getOverrideProps() {
		return overrideProps;
	}

	public void setOverrideProps(List<Property> overrideProps) {
		this.overrideProps = overrideProps;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public float getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
	}

}
