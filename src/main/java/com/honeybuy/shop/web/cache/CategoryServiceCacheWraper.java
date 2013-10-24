package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.CategoryService;
import com.hb.core.shared.dto.CategoryTreeDTO;

@Service
public class CategoryServiceCacheWraper {
	
	@Autowired
	private CategoryService categoryService;
	
	@Cacheable(cacheName="CategoryServiceCacheWraper_getCategoryTree")
	public List<CategoryTreeDTO> getCategoryTree(Integer id){
		List<CategoryTreeDTO> categoryTree = categoryService.getCategoryTree(id);
		if(categoryTree.size() > 12) {
			return categoryTree.subList(0, 12);
		}
		return categoryTree;
	}
}
