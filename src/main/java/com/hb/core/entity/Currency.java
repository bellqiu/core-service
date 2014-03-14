package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@NamedQuery(name="QueryCurrencyByName", query="select c from Currency as c where name=:name"),
	@NamedQuery(name="QueryCurrencyByCode", query="select c from Currency as c where code=:code"),
	@NamedQuery(name="countAllCurrency", query="select count(c.id) from Currency as c"),
	@NamedQuery(name="retrieveAllCurrency", query="select c from Currency as c order by c.name")
})
public class Currency extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6244763668841135644L;
	
	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="default_currency")
	private boolean defaultCurrency;
	
	@Column(name="exchange_rate_base_on_default")
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
