/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hb.core.shared.dto.CategoryDetailDTO;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.dto.PageMeta;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class PageMetaInterceptor extends HandlerInterceptorAdapter{
	
	private String pageType;
	
	@Autowired
	private SettingServiceCacheWrapper settingService;
	
	public static final String HOME_TITLE = "HOME_TITLE";
	public static final String HOME_KEYWORDS = "HOME_KEYWORDS";
	public static final String HOME_DESCRIPTION = "HOME_DESCRIPTION";
	
	public static final String DEFAULT_TITLE = "DEFAULT_TITLE";
	public static final String DEFAULT_KEYWORDS = "DEFAULT_KEYWORDS";
	public static final String DEFAULT_DESCRIPTION = "DEFAULT_DESCRIPTION";
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		PageMeta meta = new PageMeta();
		meta.setTitle(settingService.getStringValue(DEFAULT_TITLE));
		meta.setKeywords(settingService.getStringValue(DEFAULT_KEYWORDS));
		meta.setDescription(settingService.getStringValue(DEFAULT_DESCRIPTION));
		
		if("home".equals(pageType)){
			meta.setTitle(settingService.getStringValue(HOME_TITLE));
			meta.setKeywords(settingService.getStringValue(HOME_KEYWORDS));
			meta.setDescription(settingService.getStringValue(HOME_DESCRIPTION));
		}else if("category".equals(pageType)){
			CategoryDetailDTO categoryDetailDTO = (CategoryDetailDTO) modelAndView.getModel().get("currentCategoryDetail");
			if(null != categoryDetailDTO){
				meta.setDescription(categoryDetailDTO.getDescription());
				meta.setTitle(categoryDetailDTO.getPageTitle());
				meta.setKeywords(categoryDetailDTO.getRelatedKeyword());
			}
		}else if("freedomPage".equals(pageType)){
			String pageName = (String) modelAndView.getModel().get("freedompage");
			meta.setTitle(settingService.getStringValue(pageName+".TITLE"));
			meta.setKeywords(settingService.getStringValue(pageName+".KEYWORDS"));
			meta.setDescription(settingService.getStringValue(pageName+".DESCRIPTION"));
		}
		
		modelAndView.addObject("pageMeta", meta);
		
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

}
