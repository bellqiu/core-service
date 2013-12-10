/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.interceptor;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hb.core.util.Constants;
import com.honeybuy.shop.web.eds.SiteDirectService;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class TrackingCookieInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private SiteDirectService siteService;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
		Cookie[] cookies = request.getCookies();
		
		boolean tracked = false;
		String tackingId = null;
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if(Constants.TRACKING_COOKE_ID_NAME.equals(cookie.getName())){
					if(null != tackingId){
						cookie.setValue(tackingId);
					}else{
						tackingId = cookie.getValue();
					}
					cookie.setPath("/");
					tracked = true;
					response.addCookie(cookie);
				}
			}
		}
		
		if(!tracked){
			
			Cookie trackCookie = new Cookie(Constants.TRACKING_COOKE_ID_NAME, UUID.randomUUID().toString());
			trackCookie.setMaxAge(365*24*3600);
			trackCookie.setPath("/");
			response.addCookie(trackCookie);
		}
	}

	public SiteDirectService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteDirectService siteService) {
		this.siteService = siteService;
	}
	
}
