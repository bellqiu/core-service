/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Preparing site DTO");
		request.setAttribute(SITE_ATTR, siteService.getSite());
		return true;
	}

	public SiteDirectService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteDirectService siteService) {
		this.siteService = siteService;
	}

}
