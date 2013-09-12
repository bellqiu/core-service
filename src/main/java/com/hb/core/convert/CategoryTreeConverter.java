package com.hb.core.convert;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.shared.dto.CategoryTreeDTO;

@Service("categoryTreeConverter")
@Transactional(readOnly = true)
public class CategoryTreeConverter implements Converter<CategoryTreeDTO, Category>{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public CategoryTreeDTO convert(Category category) {
		CategoryTreeDTO categoryTreeDTO  = new CategoryTreeDTO();
		categoryTreeDTO.setDisplayName(category.getDisplayName());
		categoryTreeDTO.setId(category.getId());
		categoryTreeDTO.setLeaf(category.getSubCategory() == null || category.getSubCategory().size() < 1 );
		categoryTreeDTO.setName(category.getName());
		categoryTreeDTO.setType(category.getType());
		return categoryTreeDTO;
	}

	@Override
	public Category transf(CategoryTreeDTO categoryTreeDTO) {
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
		
		return category;
		
	}

}
