/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hb.core.entity.Currency;
import com.honeybuy.shop.web.eds.SiteDirectService;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class SiteDataInterceptor extends HandlerInterceptorAdapter{
	
	
	@Autowired
	private SiteDirectService siteService;
	
	
	public static final String SITE_ATTR = "site";
	public static final String CURRENCY_ATTR = "currencies";
	public static final String DEFAULT_CURRENCY_ATTR = "defaultCurrency";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Currency defaultCurrency = (Currency) request.getSession().getAttribute("defaultCurrency");
		
		List<Currency> currencies = siteService.getAllCurrency();

		String selectedCurrency = request.getParameter("currency");
		
		if(!StringUtils.isEmpty(selectedCurrency)){
			for (Currency currency : currencies) {
				if(currency.getCode().equals(selectedCurrency)){
					defaultCurrency = currency;
				}
			}
		}
		
		if(null == defaultCurrency){
			for (Currency currency : currencies) {
				if(currency.isDefaultCurrency()){
					defaultCurrency = currency;
				}
			}
		}
		
		if(null == defaultCurrency){
			Currency currency = new Currency();
			currency.setCode("USD");
			currency.setName("US Dollar");
			currency.setExchangeRateBaseOnDefault(1);
			currency.setDefaultCurrency(true);
			if(currencies.size()>0){
				defaultCurrency =  currencies.get(0);
			}else{
				defaultCurrency = currency;
				currencies.add(defaultCurrency);
			}
		}
		
		request.getSession().setAttribute(DEFAULT_CURRENCY_ATTR, defaultCurrency);
		
		request.setAttribute(SITE_ATTR, siteService.getSite());
		request.setAttribute(CURRENCY_ATTR, currencies);
		return true;
	}

	public SiteDirectService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteDirectService siteService) {
		this.siteService = siteService;
	}

}
