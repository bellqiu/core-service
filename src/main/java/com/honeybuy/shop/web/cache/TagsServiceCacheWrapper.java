package com.honeybuy.shop.web.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.shared.dto.TabProductDTO;
import com.hb.core.util.Constants;

@Service
@Transactional(readOnly=true)
public class TagsServiceCacheWrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(TagsServiceCacheWrapper.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductServiceCacheWrapper productCacheService;
	
	public static final String TAG_DIGITAL_INDEX = "0-9";
	
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
				String index;
				if(Character.isDigit(tagName.charAt(0))) {
					index = TAG_DIGITAL_INDEX;
				} else {
					index = tagName.substring(0, 1);
				}
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

	public int getTagsCountByIndex(String indexName) {
		Set<String> tagSet = getAllTagIndexMap().get(indexName);
		return tagSet == null ? 0 : tagSet.size();
	}

	public List<String> getTagsByIndexWithLimit(String indexName, int start,
			int max) {
		Set<String> tagSet = getAllTagIndexMap().get(indexName);
		int size = 0;
		if(tagSet != null && start < (size = tagSet.size())) {
			int returnSize = Math.min(max, size - start);
			List<String> tagsList = new ArrayList<String>(returnSize);
			Iterator<String> iterator = tagSet.iterator();
			int i;
			for(i = 0; i < start; i++) {
				iterator.next();
			}
			for(i = 0; i < returnSize; i++) {
				tagsList.add(iterator.next());
			}
			return tagsList;
		}
		return null;
	}

	public String existTag(String tagName) {
		if(StringUtils.isEmpty(tagName)) {
			return null;
		}
		String realTagName = tagName.replace(Constants.HYPHEN_CHAR, Constants.SPACE_CHAR);
		TreeMap<String, Set<Long>> tagsProductMap = getAllTagsProductMap();
		if(tagsProductMap.containsKey(realTagName)) {
			return realTagName;
		} else {
			Set<String> keySet = tagsProductMap.keySet();
			for(String key : keySet) {
				if(key.replace(Constants.SPACE_CHAR, Constants.HYPHEN_CHAR).equals(tagName)) {
					return key;
				}
			}
		}
		return null;
	}
	
	public int getProductCountByTag(String tagName) {
		Set<Long> productSet = getAllTagsProductMap().get(tagName);
		return productSet == null ? 0 : productSet.size();
	}
	
	public List<ProductSummaryDTO> getProductByTagWithLimit(String tagName, int start,
			int max) {
		Set<Long> productSet = getAllTagsProductMap().get(tagName);
		int size = 0;
		if(productSet != null && start < (size = productSet.size())) {
			int returnSize = Math.min(max, size - start);
			List<ProductSummaryDTO> products = new ArrayList<ProductSummaryDTO>(returnSize);
			Iterator<Long> iterator = productSet.iterator();
			int i;
			for(i = 0; i < start; i++) {
				iterator.next();
			}
			for(i = 0; i < returnSize; i++) {
				ProductSummaryDTO productSummary = productCacheService.getProductSummaryById(iterator.next());
				products.add(productSummary);
			}
			return products;
		}
		return null;
	}
	
	public List<ProductSummaryDTO> getTabProductByName(String tabKey) {
		TabProductDTO tabProduct = productCacheService.getTabProductByName(tabKey);
		if(tabProduct != null) {
			return tabProduct.getProducts();
		}
		return null;
	}
}