package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.ProductService;
import com.hb.core.service.TabProductService;
import com.hb.core.shared.dto.ProductChangeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
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
	
	public ProductChangeDTO compupterProductChangeByOptsAndCurrency(ProductDetailDTO productDetailDTO, String optParams){
		return productService.compupterProductChangeByOptsAndCurrency(productDetailDTO, optParams);
	}
	
	@Cacheable(cacheName="TabProduct")
	public TabProductDTO getTabProductByName(String name){
		return tabProductService.getTabProductDTOByName(name);
	}
	
	@Cacheable(cacheName="ProductCountByCategory")
	public int getProductCountByCategoryId(long id) {
		return productService.getProductCountByCategoryId(id);
	}

	@Cacheable(cacheName="ProductSummaryByCategory")
	public List<ProductSummaryDTO> getAllProductByCategoryId(long id, int start, int max) {
		return productService.getAllProductByCategoryId(id, start, max);
	}
	
	@Cacheable(cacheName="LowestPriceInCategory")
	public double getLowestPriceByCategoryId(long id) {
		return productService.getLowestPriceByCategoryId(id);
	}
	
	@Cacheable(cacheName="HighestPriceInCategory")
	public double getHighestPriceByCategoryId(long id) {
		return productService.getHighestPriceByCategoryId(id);
	}
	
	@Cacheable(cacheName="SearchProductName")
	public List<String> getProductName(long id, String columnName, String value) {
		return productService.getProductName(id, columnName, value);
	}
	
	@Cacheable(cacheName="SearchProductCountByKey")
	public int searchProductCountByKey(String key) {
		return productService.searchCountByKey(key);
	}
	
	@Cacheable(cacheName="SearchProductByKey")
	public List<ProductSummaryDTO> searchProductByKey(String key, int start, int max) {
		List<ProductSummaryDTO> productByKey = productService.searchProductByKey(key);
		int size = productByKey.size();
		if(size <= start) {
			return null;
		}
		if(size >= (start + max)) {
			return productByKey.subList(start, start + max);
		} else {
			return productByKey.subList(start, size);
		}
	}
}
