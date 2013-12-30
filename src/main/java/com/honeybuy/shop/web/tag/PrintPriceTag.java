package com.honeybuy.shop.web.tag;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.entity.Currency;
import com.honeybuy.shop.web.cache.CurrencyServiceCacheWrapper;

public class PrintPriceTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	@Autowired
	private CurrencyServiceCacheWrapper currencyService;
	
	private double price;
	private boolean withCurrency = true;
	private String currencyCode;

	@Override
	public String handle(ServletRequest request) {
		Currency currency = null;
		if(!StringUtils.isEmpty(currencyCode)) {
			try {
				currency = currencyService.getCurrencyByCode(currencyCode);
			} catch(Exception e) {
			}
		} 
		if(currency == null) {
			currency = (Currency) ((HttpServletRequest)request).getSession().getAttribute("defaultCurrency");
		}
		double newPrice = currency.getExchangeRateBaseOnDefault() * price;
		NumberFormat numberFormat = new DecimalFormat("#,###,##0.00");
		String strPrice = numberFormat.format(newPrice);
		request.setAttribute("__price", strPrice);
		request.setAttribute("__currency", currency);
		request.setAttribute("__withCurrency", withCurrency);
		return "printPrice";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("__price");
		request.removeAttribute("__currency");
		request.removeAttribute("__withCurrency");
		withCurrency = true;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public static void main(String[] args) {
		NumberFormat numberFormat = new DecimalFormat("#,###,##0.00");
		String strPrice = numberFormat.format(90);
		System.out.println(strPrice);
	}

	public boolean isWithCurrency() {
		return withCurrency;
	}

	public void setWithCurrency(boolean withCurrency) {
		this.withCurrency = withCurrency;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

}
