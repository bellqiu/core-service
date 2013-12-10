/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hb.core.entity.Currency;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.UserUtils;
import com.honeybuy.shop.web.cache.OrderServiceCacheWrapper;
import com.honeybuy.shop.web.eds.SiteDirectService;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class BootstrapDataInterceptor extends HandlerInterceptorAdapter{
	
	
	@Autowired
	private SiteDirectService siteService;
	
	@Autowired
	private OrderServiceCacheWrapper orderService;
	
	
	public static final String SITE_ATTR = "site";
	public static final String CART_ITEM_COUNT = "cartItemCount";
	public static final String CURRENCY_ATTR = "currencies";
	public static final String DEFAULT_CURRENCY_ATTR = "defaultCurrency";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		handlerSiteData(request);
		
		handlerDefaultCurrency(request, response);
		
		handlerCartItemCount(request);
		
		return true;
	}

	private void handlerCartItemCount(HttpServletRequest request) {
		String trackingId = null;
		
		Cookie[] cookies = request.getCookies();
		
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if(Constants.TRACKING_COOKE_ID_NAME.equals(cookie.getName())){
					trackingId = cookie.getValue();
				}
			}
		}
		
		String email = null;
		
		UserDetails user = UserUtils.getCurrentUser();
		
		if(null != user){
			email = user.getUsername();
		}
		
		request.setAttribute(CART_ITEM_COUNT, orderService.getCartItemCount(trackingId, email));
	}

	private void handlerSiteData(HttpServletRequest request) {
		request.setAttribute(SITE_ATTR, siteService.getSite());
	}

	private void handlerDefaultCurrency(HttpServletRequest request,
			HttpServletResponse response) {
		Currency defaultCurrency = (Currency) request.getSession().getAttribute(DEFAULT_CURRENCY_ATTR);
		String cookieCurrency  = null;
		if(null == defaultCurrency ){
			Cookie[] cookies = request.getCookies();
			if(null != cookies){
				for (Cookie cookie : cookies) {
					if(Constants.CURRENCY_COOKE_ID_NAME.equals(cookie.getName())){
						cookieCurrency = cookie.getValue();
					}
				}
			}
		}
		
		List<Currency> currencies = siteService.getAllCurrency();

		String selectedCurrency = request.getParameter("currency");
		
		selectedCurrency = selectedCurrency==null? cookieCurrency : selectedCurrency;
		
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
		
		
		request.setAttribute(CURRENCY_ATTR, currencies);
		
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if(Constants.CURRENCY_COOKE_ID_NAME.equals(cookie.getName())){
					cookie.setPath("/");
					cookie.setValue(defaultCurrency.getCode());
					cookie.setMaxAge(365*24*3600);
					response.addCookie(cookie);
				}
			}
		}
		
		Cookie currencyCookie = new Cookie(Constants.CURRENCY_COOKE_ID_NAME, defaultCurrency.getCode());
		currencyCookie.setMaxAge(365*24*3600);
		currencyCookie.setPath("/");
		response.addCookie(currencyCookie);
	}

	public SiteDirectService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteDirectService siteService) {
		this.siteService = siteService;
	}

	public OrderServiceCacheWrapper getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderServiceCacheWrapper orderService) {
		this.orderService = orderService;
	}

}
