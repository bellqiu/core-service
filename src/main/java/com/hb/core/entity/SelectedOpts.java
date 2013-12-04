package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelectedOpts extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5825040052270112825L;
	@Column(name="value")
	private String value;
	
	private float priceChange;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public float getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
	}

}
