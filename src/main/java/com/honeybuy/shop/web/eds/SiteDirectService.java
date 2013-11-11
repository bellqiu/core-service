/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web.eds;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.entity.Currency;
import com.hb.core.service.CurrencyService;
import com.hb.core.service.SettingService;
import com.hb.core.shared.dto.SiteDTO;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Service
public class SiteDirectService{
	
	public static final String RESOURCES_SERVER = "RESOURCES_SERVER";
	public static final String DOMAIN_SERVER = "DOMAIN_SERVER";
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private CurrencyService currencyService;

	@Cacheable(cacheName="siteDTO")
	public SiteDTO getSite() {
		SiteDTO siteDTO = new SiteDTO();
		
		siteDTO.setResourceServer(settingService.getStringValue(RESOURCES_SERVER, "http://localhost"));
		siteDTO.setWebResourcesFolder("/rs");
		siteDTO.setProductImageResourcesFolder("/img");
		
		List<String> css = new ArrayList<String>();
		List<String> js = new ArrayList<String>();
		
		siteDTO.setCss(css);
		siteDTO.setJs(js);
		
		siteDTO.setSiteName("HoneyBuy");
		siteDTO.setDomain(settingService.getStringValue(DOMAIN_SERVER, "http://localhost"));
		
		
		return siteDTO;
	}
	
	@Cacheable(cacheName="allCurrency")
	public List<Currency> getAllCurrency(){
		return currencyService.getAllCurrency();
	}

}
