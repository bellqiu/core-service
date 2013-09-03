package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries(value=
	{
		@NamedQuery(name="QueryCountryByKey", query="select c from Country as c where code=:key"),
		@NamedQuery(name="countAllCountry", query="select count(c.id) from Country as c"),
	})
public class Country extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 168229983824448541L;
	
	private String code;
	private String abbrCode;
	@Column(name = "normal_delivery_price")
	private float normalDeliveryPrice;
	@Column(name = "advance_delivery_price")
	private float advanceDeliveryPrice;
	@Column(name = "free_delivery_price")
	private float freeDeliveryPrice;
	@Column(name = "free_advance_delivery_price")
	private float freeAdvanceDeliveryPrice;
	
	public Country() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAbbrCode() {
		return abbrCode;
	}

	public void setAbbrCode(String abbrCode) {
		this.abbrCode = abbrCode;
	}

	public float getNormalDeliveryPrice() {
		return normalDeliveryPrice;
	}

	public void setNormalDeliveryPrice(float normalDeliveryPrice) {
		this.normalDeliveryPrice = normalDeliveryPrice;
	}

	public float getAdvanceDeliveryPrice() {
		return advanceDeliveryPrice;
	}

	public void setAdvanceDeliveryPrice(float advanceDeliveryPrice) {
		this.advanceDeliveryPrice = advanceDeliveryPrice;
	}

	public float getFreeDeliveryPrice() {
		return freeDeliveryPrice;
	}

	public void setFreeDeliveryPrice(float freeDeliveryPrice) {
		this.freeDeliveryPrice = freeDeliveryPrice;
	}

	public float getFreeAdvanceDeliveryPrice() {
		return freeAdvanceDeliveryPrice;
	}

	public void setFreeAdvanceDeliveryPrice(float freeAdvanceDeliveryPrice) {
		this.freeAdvanceDeliveryPrice = freeAdvanceDeliveryPrice;
	}
	
}
