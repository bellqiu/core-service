/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.eds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.entity.Currency;
import com.hb.core.service.SettingService;
import com.hb.core.shared.dto.SiteDTO;
import com.honeybuy.shop.web.cache.CurrencyServiceCacheWrapper;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Service
public class SiteDirectService{
	
	public static final String RESOURCES_SERVER = "RESOURCES_SERVER";
	public static final String DOMAIN_SERVER = "DOMAIN_SERVER";
	public static final String SITE_CUSTOMIZED_CSS = "SITE_CUSTOMIZED_CSS";
	public static final String SITE_CUSTOMIZED_JS = "SITE_CUSTOMIZED_JS";
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private CurrencyServiceCacheWrapper currencyService;

	@Cacheable(cacheName="siteDTO")
	public SiteDTO getSite() {
		SiteDTO siteDTO = new SiteDTO();
		
		siteDTO.setResourceServer(settingService.getStringValue(RESOURCES_SERVER, "http://localhost"));
		siteDTO.setWebResourcesFolder("/rs");
		siteDTO.setProductImageResourcesFolder("/img");
		
		String css = null;
		String js = null;
		if((css = settingService.getStringValue(SITE_CUSTOMIZED_CSS)) != null) {
			siteDTO.setCss(Arrays.asList(css.split(",")));
		}
		if((js = settingService.getStringValue(SITE_CUSTOMIZED_JS)) != null) {
			siteDTO.setJs(Arrays.asList(js.split(",")));
		}
		
		siteDTO.setSiteName("HoneyBuy");
		siteDTO.setDomain(settingService.getStringValue(DOMAIN_SERVER, "http://localhost"));
		
		return siteDTO;
	}
	
	public List<Currency> getAllCurrency(){
		return currencyService.getAllCurrency();
	}

}
