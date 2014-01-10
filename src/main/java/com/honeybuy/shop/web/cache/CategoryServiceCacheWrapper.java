package com.honeybuy.shop.web.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.CategoryService;
import com.hb.core.shared.dto.CategoryDetailDTO;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.honeybuy.shop.util.CloneUtil;

@Service
@Transactional(readOnly=true)
public class CategoryServiceCacheWrapper {
	
	@Autowired
	private CategoryService categoryService;
	
	@Cacheable(cacheName="GetCategoryTree")
	public List<CategoryTreeDTO> getCategoryTree(long id){
		List<CategoryTreeDTO> categoryTree = categoryService.getNomalCategoryTree(id);
		if(categoryTree.size() > 12) {
			return categoryTree.subList(0, 12);
		}
		return CloneUtil.<List<CategoryTreeDTO>>cloneThroughJson(categoryTree);
	}
	
	@Cacheable(cacheName="GetSpecialCategoryTree")
	public List<CategoryTreeDTO> getSpecialCategoryTree(long id){
		List<CategoryTreeDTO> categoryTree = categoryService.getSpecialCategoryTree(id);
		if(categoryTree.size() > 12) {
			return categoryTree.subList(0, 12);
		}
		return CloneUtil.<List<CategoryTreeDTO>>cloneThroughJson(categoryTree);
	}
	
	@Cacheable(cacheName="CategoryDetail")
	public CategoryDetailDTO getCategoryDetailByName(String name){
		
		CategoryDetailDTO detailDTO = categoryService.getCategoryDetailDTOByName(name);
		
		return CloneUtil.<CategoryDetailDTO>cloneThroughJson(detailDTO);
	}

	@Cacheable(cacheName="CategoryBreadcrumb")
	public List<String> getCategoryBreadcrumb(long id) {
		return categoryService.getCategoryBreadcrumbById(id);
	}
	
	@Cacheable(cacheName="GetSubCategories")
	public List<CategoryTreeDTO> getSubCategories(long id){
		List<CategoryTreeDTO> categoryTree = categoryService.getCategoryTree(id);
		/*if(categoryTree.size() > 12) {
			return categoryTree.subList(0, 12);
		}*/
		return CloneUtil.<List<CategoryTreeDTO>>cloneThroughJson(categoryTree);
	}
	
	@Cacheable(cacheName="GetSubCategoryByName")
	public List<CategoryTreeDTO> getSubCategoryName(List<String> parentNames){
		List<CategoryTreeDTO> categoryTree = categoryService.getCategoryTreeByName(parentNames);
		return CloneUtil.<List<CategoryTreeDTO>>cloneThroughJson(categoryTree);
	}
	
	@Cacheable(cacheName="GetCategoryWithAllSubCategories")
	public List<Long> getCategoryIdWithAllSubCategories(long id){
		List<Long> categoryTree = categoryService.getAllSubCategoryTree(id);
		return categoryTree;
	}

	public String queryCategoryName(String startKey) {
		return categoryService.queryCategoryNameWithStartKey(startKey);
	}
}
