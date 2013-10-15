package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.SettingService;

@Service
public class SettingServiceCacheWraper {
	@Autowired
	private SettingService settingService;
	
	@Cacheable(cacheName="SettingServiceCacheWraper_getStringValue")
	public String getStringValue(String key){
		return settingService.getStringValue(key);
	}
}
