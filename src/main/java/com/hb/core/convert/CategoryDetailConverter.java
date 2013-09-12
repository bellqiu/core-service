package com.hb.core.convert;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.shared.dto.CategoryDetailDTO;

@Service("categoryDetailConverter")
@Transactional(readOnly = true)
public class CategoryDetailConverter implements
		Converter<CategoryDetailDTO, Category> {
	
	public CategoryDetailConverter() {
		System.out.println("##################\n################\n######################\n#########################");
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public CategoryDetailDTO convert(Category category) {
		CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO();
		categoryDetailDTO.setType(category.getType());
		categoryDetailDTO.setDisplayName(category.getDisplayName());
		categoryDetailDTO.setIconUrl(category.getIconUrl());
		categoryDetailDTO.setMarketContent(category.getMarketContent());
		categoryDetailDTO.setName(category.getName());
		categoryDetailDTO.setPageTitle(category.getPageTitle());
		categoryDetailDTO.setRelatedKeyword(category.getRelatedKeyword());
		categoryDetailDTO.setUrl(category.getUrl());
		categoryDetailDTO.setId(category.getId());
		categoryDetailDTO.setParentId(category.getParent() != null ? category
				.getParent().getId() : 0);
		return categoryDetailDTO;
	}

	@Override
	public Category transf(CategoryDetailDTO categoryDetailDTO) {
		Category category = new Category();

		if (categoryDetailDTO.getId() > 0) {
			category = em.find(Category.class, categoryDetailDTO.getId());
		}

		if (categoryDetailDTO.getParentId() > 0) {
			category.setParent(em.find(Category.class,
					categoryDetailDTO.getId()));
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
		return category;
	}

}
