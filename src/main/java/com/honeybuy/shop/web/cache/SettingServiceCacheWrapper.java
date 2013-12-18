package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.SettingService;

@Service
@Transactional(readOnly=true)
public class SettingServiceCacheWrapper {
	@Autowired
	private SettingService settingService;
	
	@Cacheable(cacheName="setting")
	public String getStringValue(String key){
		return settingService.getStringValue(key);
	}
	
	public String getStringValue(String key, String defaultValue){
		String value = getStringValue(key);
		if(value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}
}
