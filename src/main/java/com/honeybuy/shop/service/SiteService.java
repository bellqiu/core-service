/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.shared.dto.SiteDTO;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Service
public class SiteService implements ISiteService{

	@Override
	@Cacheable(cacheName="siteDTO")
	public SiteDTO getSite() {
		System.out.println("Getting site info.......................");
		SiteDTO siteDTO = new SiteDTO();
		
		siteDTO.setResourceServer("http://localhost:88");
		siteDTO.setWebResourcesFolder("/resources");
		siteDTO.setProductImageResourcesFolder("/image");
		
		List<String> css = new ArrayList<String>();
		List<String> js = new ArrayList<String>();
		
		siteDTO.setCss(css);
		siteDTO.setJs(js);
		
		siteDTO.setSiteName("HoneyBuy");
		siteDTO.setDomain("http://localhost:88");
		
		
		return siteDTO;
	}

}
