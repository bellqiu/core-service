package com.honeybuy.shop.web.tag;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.hb.core.entity.Currency;

public class PrintPriceTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	private double price;

	@Override
	public String handle(ServletRequest request) {
		Currency currency = (Currency) ((HttpServletRequest)request).getSession().getAttribute("defaultCurrency");
		double newPrice = currency.getExchangeRateBaseOnDefault() * price;
		NumberFormat numberFormat = new DecimalFormat("#,###,##0.00");
		String strPrice = numberFormat.format(newPrice);
		request.setAttribute("__price", strPrice);
		request.setAttribute("__currency", currency);
		return "printPrice";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("__price");
		request.removeAttribute("__currency");
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

}
