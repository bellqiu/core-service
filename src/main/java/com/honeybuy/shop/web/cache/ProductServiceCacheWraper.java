package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.ProductDetailDTO;

@Service
@Transactional(readOnly=true)
public class ProductServiceCacheWraper {
	@Autowired
	private ProductService productService;
	
	@Cacheable(cacheName="ProductNameExisting")
	public boolean exist(String name){
		return productService.exist(name);
	}
	
	@Cacheable(cacheName="Product")
	public ProductDetailDTO getProductDetailByName(String name){
		return productService.getProductDetailByName(name);
	}
}
