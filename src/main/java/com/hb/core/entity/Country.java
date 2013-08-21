package com.hb.core.entity;

public class Country extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 168229983824448541L;
	
	private String code;
	private String abbrCode;
	private float normalDeliveryPrice;
	private float advanceDeliveryPrice;
	private float freeDeliveryPrice;
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
