package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OptionItem extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3876490399646625342L;

	@Column(name = "value")
	private String value;
	
	@Column(name = "icon_url")
	private String iconUrl;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "price_change")
	private float priceChange;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="OptionItem_has_Property",
				joinColumns=@JoinColumn(name="OptionItem_id"),
				inverseJoinColumns=@JoinColumn(name="Property_id"))
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
