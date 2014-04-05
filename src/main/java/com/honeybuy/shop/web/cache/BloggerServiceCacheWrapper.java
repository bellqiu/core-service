package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.entity.Blogger;
import com.hb.core.service.BloggerService;

@Service
@Transactional(readOnly=true)
public class BloggerServiceCacheWrapper {
	
	@Autowired
	private BloggerService bloggerService;

	@Cacheable(cacheName="BloggerCount")
	public int getActiveBloggerCount() {
		int count = bloggerService.getActiveBloggerCount();
		//return count;
		return 0;
	}
	
	@Cacheable(cacheName="BloggerList")
	public List<Blogger> getAllActiveBlogger(int start, int max) {
		return bloggerService.getAllActiveBlogger(start, max);
	}
}
