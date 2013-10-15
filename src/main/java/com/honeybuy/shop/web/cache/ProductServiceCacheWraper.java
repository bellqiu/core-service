package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.ProductService;

@Service
public class ProductServiceCacheWraper {
	@Autowired
	private ProductService productService;
	
	@Cacheable(cacheName="ProductNameExisting")
	public boolean exist(String name){
		return productService.exist(name);
	}
}
