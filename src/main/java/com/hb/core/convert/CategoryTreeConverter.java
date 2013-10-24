package com.hb.core.convert;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hb.core.entity.Category;
import com.hb.core.shared.dto.CategoryTreeDTO;

@org.springframework.stereotype.Component
public class CategoryTreeConverter implements Converter<CategoryTreeDTO, Category>{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public CategoryTreeDTO convert(Category category) {
		
		if(null == category){
			return null;
		}
		
		CategoryTreeDTO categoryTreeDTO  = new CategoryTreeDTO();
		categoryTreeDTO.setDisplayName(category.getDisplayName());
		categoryTreeDTO.setId(category.getId());
		categoryTreeDTO.setLeaf(category.getSubCategory() == null || category.getSubCategory().size() < 1 );
		categoryTreeDTO.setName(category.getName());
		categoryTreeDTO.setType(category.getType());
		categoryTreeDTO.setIconUrl(category.getIconUrl());
		categoryTreeDTO.setUrl(category.getUrl());
		
		Category parent = category.getParent();
		
		categoryTreeDTO.setParentId(null!=parent ? parent.getId() : 0L);
		
		return categoryTreeDTO;
	}

	@Override
	public Category transf(CategoryTreeDTO categoryTreeDTO) {
		
		if(null == categoryTreeDTO){
			return null;
		}
		
		
		Category category = new Category();

		if (categoryTreeDTO.getId() > 0) {
			category = em.find(Category.class, categoryTreeDTO.getId());
		}
		
		category.setCreateDate(category.getCreateDate() == null ? new Date()
				: category.getCreateDate());
		category.setUpdateDate(new Date());
		
		category.setName(categoryTreeDTO.getName());
		category.setType(categoryTreeDTO.getType());
		category.setDisplayName(categoryTreeDTO.getDisplayName());
		category.setIconUrl(categoryTreeDTO.getIconUrl());
		category.setUrl(categoryTreeDTO.getUrl());
		
		if(categoryTreeDTO.getParentId() > 0 ){
			category.setParent(em.find(Category.class, categoryTreeDTO.getParentId()));
		}
		
		return category;
		
	}

}
