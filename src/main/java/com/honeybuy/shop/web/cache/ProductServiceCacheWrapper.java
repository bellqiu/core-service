package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.ProductService;
import com.hb.core.service.TabProductService;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.TabProductDTO;

@Service
@Transactional(readOnly=true)
public class ProductServiceCacheWrapper {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private TabProductService tabProductService;
	
	@Cacheable(cacheName="ProductNameExisting")
	public boolean exist(String name){
		return productService.exist(name);
	}
	
	@Cacheable(cacheName="Product")
	public ProductDetailDTO getProductDetailByName(String name){
		return productService.getProductDetailByName(name);
	}
	
	@Cacheable(cacheName="TabProduct")
	public TabProductDTO getTabProductByName(String name){
		return tabProductService.getTabProductDTOByName(name);
	}
}
