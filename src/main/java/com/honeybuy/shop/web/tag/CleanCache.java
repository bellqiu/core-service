package com.honeybuy.shop.web.tag;

import javax.servlet.ServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CleanCache extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	@Autowired
	@Qualifier("hbCacheManager")
	private CacheManager cacheManager;
	

	@Override
	public String handle(ServletRequest request) {
		String el = request.getParameter("el");
		if(null != el){
			Cache cache = cacheManager.getCache(el);
			if(null != cache){
				cache.removeAll();
			}
		}
		
		return null;
	}
	
	@Override
	public void clean(ServletRequest request) {
	}
	
	@Override
	public void release() {
	}
	

}
