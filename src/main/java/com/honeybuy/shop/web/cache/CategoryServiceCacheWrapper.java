package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.CategoryService;
import com.hb.core.shared.dto.CategoryDetailDTO;
import com.hb.core.shared.dto.CategoryTreeDTO;

@Service
@Transactional(readOnly=true)
public class CategoryServiceCacheWrapper {
	
	@Autowired
	private CategoryService categoryService;
	
	@Cacheable(cacheName="GetCategoryTree")
	public List<CategoryTreeDTO> getCategoryTree(int id){
		List<CategoryTreeDTO> categoryTree = categoryService.getNomalCategoryTree(id);
		if(categoryTree.size() > 12) {
			return categoryTree.subList(0, 12);
		}
		return categoryTree;
	}
	
	@Cacheable(cacheName="GetSpecialCategoryTree")
	public List<CategoryTreeDTO> getSpecialCategoryTree(int id){
		List<CategoryTreeDTO> categoryTree = categoryService.getSpecialCategoryTree(id);
		if(categoryTree.size() > 12) {
			return categoryTree.subList(0, 12);
		}
		return categoryTree;
	}
	
	@Cacheable(cacheName="CategoryDetail")
	public CategoryDetailDTO getCategoryDetailByName(String name){
		return categoryService.getCategoryDetailDTOByName(name);
	}

	@Cacheable(cacheName="CategoryBreadcrumb")
	public List<String> getCategoryBreadcrumb(long id) {
		return categoryService.getCategoryBreadcrumbById(id);
	}
}
