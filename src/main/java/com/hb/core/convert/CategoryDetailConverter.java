package com.hb.core.convert;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hb.core.entity.Category;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.CategoryDetailDTO;

@org.springframework.stereotype.Component
public class CategoryDetailConverter implements
		Converter<CategoryDetailDTO, Category> {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public CategoryDetailDTO convert(Category category) {
		
		if(null == category){
			return null;
		}
		
		CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO();
		categoryDetailDTO.setType(category.getType());
		categoryDetailDTO.setDisplayName(category.getDisplayName());
		categoryDetailDTO.setIconUrl(category.getIconUrl());
		categoryDetailDTO.setMarketContent(category.getMarketContent());
		categoryDetailDTO.setName(category.getName());
		categoryDetailDTO.setPageTitle(category.getPageTitle());
		categoryDetailDTO.setRelatedKeyword(category.getRelatedKeyword());
		categoryDetailDTO.setUrl(category.getUrl());
		categoryDetailDTO.setDescription(category.getDescription());
		categoryDetailDTO.setPriority(category.getPriority());
		categoryDetailDTO.setId(category.getId());
		categoryDetailDTO.setParentId(category.getParent() != null ? category
				.getParent().getId() : 0);
		return categoryDetailDTO;
	}

	@Override
	public Category transf(CategoryDetailDTO categoryDetailDTO) {
		
		if(null == categoryDetailDTO){
			return null;
		}
		
		Category category = new Category();

		if (categoryDetailDTO.getId() > 0) {
			category = em.find(Category.class, categoryDetailDTO.getId());
		}

		long parentId = categoryDetailDTO.getParentId();
		if (parentId > 0) {
			if(parentId == categoryDetailDTO.getId()) {
				throw new CoreServiceException("Parent id should not be same as its id");
			} else {
				category.setParent(em.find(Category.class,
					parentId));
			}
		} else {
			category.setParent(null);
		}
		category.setCreateDate(category.getCreateDate() == null ? new Date()
				: category.getCreateDate());
		category.setUpdateDate(new Date());
		category.setType(categoryDetailDTO.getType());
		category.setDisplayName(categoryDetailDTO.getDisplayName());
		category.setIconUrl(categoryDetailDTO.getIconUrl());
		category.setMarketContent(categoryDetailDTO.getMarketContent());
		category.setName(categoryDetailDTO.getName());
		category.setPageTitle(categoryDetailDTO.getPageTitle());
		category.setRelatedKeyword(categoryDetailDTO.getRelatedKeyword());
		category.setUrl(categoryDetailDTO.getUrl());
		category.setDescription(categoryDetailDTO.getDescription());
		category.setPriority(categoryDetailDTO.getPriority());
		return category;
	}

}
