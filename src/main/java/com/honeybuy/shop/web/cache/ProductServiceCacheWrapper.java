package com.honeybuy.shop.web.cache;

import java.util.List;

import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.CloneUtil;

@Service
@Transactional(readOnly=true)
public class ProductServiceCacheWrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceCacheWrapper.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private TabProductService tabProductService;
	
	@Autowired
	private CategoryServiceCacheWrapper categoryCacheService;
	
	@Cacheable(cacheName="ProductNameExisting")
	public boolean exist(String name){
		return productService.exist(name);
	}
	
	@Cacheable(cacheName="Product")
	public ProductDetailDTO getProductDetailByName(String name){
		
		logger.debug("HIT get getProductDetailByName={}", name);
		
		 ProductDetailDTO detail = productService.getProductDetailByName(name);
		 
		 
		 return CloneUtil.<ProductDetailDTO>cloneThroughJson(detail);
	}
	
	@Cacheable(cacheName="ProductChange")
	public ProductChangeDTO getProductDetailChangeByNameAndParams(String name, String optParams ){
		
		logger.debug("HIT get getProductDetailChangeByName={}, param={}", new Object[]{name,optParams});
		
		 ProductChangeDTO detail = productService.compupterProductChangeByOpts(name, optParams);
		 
		 return CloneUtil.<ProductChangeDTO>cloneThroughJson(detail);
	}
	
	@Cacheable(cacheName="TabProduct")
	public TabProductDTO getTabProductByName(String name){
		TabProductDTO dto = tabProductService.getTabProductDTOByName(name);
		return dto;
	}
	
	@Cacheable(cacheName="ProductCountByCategory")
	public int getProductCountByCategoryId(long categoryId) {
		List<Long> categoryIds = categoryCacheService.getCategoryIdWithAllSubCategories(categoryId);
		return productService.getProductCountByCategoryIds(categoryIds);
	}

	@Cacheable(cacheName="ProductSummaryByCategory")
	public List<ProductSummaryDTO> getAllProductByCategoryId(long id, int start, int max) {
		List<Long> categoryIds = categoryCacheService.getCategoryIdWithAllSubCategories(id);
		List<ProductSummaryDTO> dtos = productService.getAllProductByCategoryIds(categoryIds, start, max);
		return dtos;
	}
	
	public int getProductCountWithPriceRangeByCategoryId(long categoryId, double lowPrice, double highPrice) {
		List<Long> categoryIds = categoryCacheService.getCategoryIdWithAllSubCategories(categoryId);
		return productService.getProductCountWithPriceRangeByCategoryIds(categoryIds, lowPrice, highPrice);
	}
	
	public List<ProductSummaryDTO> getAllProductWithPriceRangeByCategoryId(long id, double lowPrice, double highPrice, int start, int max) {
		List<Long> categoryIds = categoryCacheService.getCategoryIdWithAllSubCategories(id);
		List<ProductSummaryDTO> dtos = productService.getAllProducttWithPriceRangeByCategoryIds(categoryIds, lowPrice, highPrice, start, max);
		
		return CloneUtil.<List<ProductSummaryDTO>>cloneThroughJson(dtos);
	}
	
	@Cacheable(cacheName="LowestPriceInCategory")
	public double getLowestPriceByCategoryId(long id) {
		List<Long> categoryIds = categoryCacheService.getCategoryIdWithAllSubCategories(id);
		return productService.getLowestPriceByCategoryId(categoryIds);
	}
	
	@Cacheable(cacheName="HighestPriceInCategory")
	public double getHighestPriceByCategoryId(long id) {
		List<Long> categoryIds = categoryCacheService.getCategoryIdWithAllSubCategories(id);
		return productService.getHighestPriceByCategoryId(categoryIds);
	}
	
	@Cacheable(cacheName="SearchProductName")
	public List<String> getProductName(long id, String columnName, String value) {
		return productService.getProductName(id, columnName, value);
	}
	
	@Cacheable(cacheName="SearchProductCountByKey")
	public int searchProductCountByKey(String key) {
		String [] keys = key.split(Constants.SPACE_CHAR);
		return productService.searchCountByKey(keys);
	}
	
	@Cacheable(cacheName="SearchProductByKey")
	public List<ProductSummaryDTO> searchProductByKey(String key, int start, int max) {
		String [] keys = key.split(Constants.SPACE_CHAR);
		List<ProductSummaryDTO> productByKey = productService.searchProductByKey(keys, start, max);

		return CloneUtil.<List<ProductSummaryDTO>>cloneThroughJson(productByKey);
	}
	
	@Cacheable(cacheName="ProductSummaryById")
	public ProductSummaryDTO getProductSummaryById(long productId){
		
		logger.debug("HIT get getProductSummaryById={}", productId);
		
		ProductSummaryDTO summary = productService.getActiveProductSummary(productId);
		 
		return CloneUtil.<ProductSummaryDTO>cloneThroughJson(summary);
	}
	
	public int getLikesByProductId(long id) {
		return productService.getLikesByProductId(id);
	}
	
	public int getSoldsByProductId(long id) {
		return productService.getSoldsByProductId(id);
	}
	
	public void setLikeSold(ProductSummaryDTO productSummaryDTO) {
		productService.setLikeSold(productSummaryDTO);
	}
	
	public void setLikeSold(List<ProductSummaryDTO> productSummaryList) {
		if(!CollectionUtils.isEmpty(productSummaryList)) {
			for(ProductSummaryDTO productSummaryDTO : productSummaryList) {
				productService.setLikeSold(productSummaryDTO);
			}
		}
	}
	
}
