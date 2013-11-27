/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.interceptor;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class TrackingCookieInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
		Cookie[] cookies = request.getCookies();
		
		boolean tracked = false;
		
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if("trackingId".equals(cookie.getName())){
					tracked = true;
				}
			}
		}
		
		if(!tracked){
			Cookie trackCookie = new Cookie("trackingId", UUID.randomUUID().toString());
			trackCookie.setMaxAge(365*24*3600);
			response.addCookie(trackCookie);
		}
	}
}
