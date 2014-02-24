package com.honeybuy.shop.web.cache;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.ProductService;

@Service
@Transactional(readOnly=true)
public class TagsServiceCacheWrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(TagsServiceCacheWrapper.class);
	
	@Autowired
	private ProductService productService;
	
	@Cacheable(cacheName="TagsWithProductIds")
	public synchronized TreeMap<String, Set<Long>> getAllTagsProductMap(){
		logger.debug("Get Tags with Product IDs");
		return productService.getAllTagsMap();
	}
	
	@Cacheable(cacheName="TagIndexMap")
	public TreeMap<String, Set<String>> getAllTagIndexMap(){
		Set<String> tagNames = getAllTagsProductMap().keySet();
		TreeMap<String, Set<String>> tagIndexMap = new TreeMap<String, Set<String>>();
		for(String tagName : tagNames) {
			if(!StringUtils.isEmpty(tagName)) {
				String index = tagName.substring(0, 1);
				Set<String> tagsSet = tagIndexMap.get(index);
				if(tagsSet != null) {
					tagsSet.add(tagName);
				} else {
					tagsSet = new TreeSet<String>();
					tagsSet.add(tagName);
					tagIndexMap.put(index, tagsSet);
				}
			}
		}
		return tagIndexMap;
	}
}