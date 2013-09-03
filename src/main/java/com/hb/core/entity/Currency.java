package com.hb.core.entity;

public class Currency extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6244763668841135644L;
	
	private String name;
	
	private String code;
	
	private boolean defaultCurrency;
	
	private float exchangeRateBaseOnDefault;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(boolean defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public float getExchangeRateBaseOnDefault() {
		return exchangeRateBaseOnDefault;
	}

	public void setExchangeRateBaseOnDefault(float exchangeRateBaseOnDefault) {
		this.exchangeRateBaseOnDefault = exchangeRateBaseOnDefault;
	}
	
	

}
