package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.BloggerService;

@Service
@Transactional(readOnly=true)
public class BloggerServiceCacheWrapper {
	
	@Autowired
	private BloggerService bloggerService;

	//@Cacheable(cacheName="BloggerCount")
	public int getActiveBloggerCount(long bloggerId) {
		//int count = bloggerService.getActiveBloggerCount(bloggerId);
		//return count;
		return 0;
	}
}
