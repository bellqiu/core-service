package com.hb.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.convert.Converter;
import com.hb.core.entity.Category;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.CategoryDetailDTO;
import com.hb.core.shared.dto.CategoryTreeDTO;

@Transactional
@Service
public class CategoryService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Converter<CategoryDetailDTO, Category> categoryDetailConverter;

	@Autowired
	private Converter<CategoryTreeDTO, Category>  categoryTreeConverter;

	public Category saveOrUpdate(Category category) {
		if (category.getId() < 1) {
			if (null != getCategoryByName(category.getName())) {
				throw new CoreServiceException("Category already exist");
			}
		}
		category = em.merge(category);
		return category;
	}

	/*
	 * public void destory(Category category){ Category category =
	 * em.merge(category); em.remove(category); }
	 */

	public CategoryDetailDTO saveCategoryDetail(
			CategoryDetailDTO categoryDetailDTO) {

		Category existingCategory = getCategoryByName(categoryDetailDTO
				.getName());
		if (categoryDetailDTO.getId() < 1) {
			if (null != existingCategory) {
				throw new CoreServiceException("Category already exist");
			}
		} else if (null != existingCategory
				&& existingCategory.getId() != categoryDetailDTO.getId()) {
			throw new CoreServiceException("This name is not available");
		}

		Category category = categoryDetailConverter.transf(categoryDetailDTO);

		category = em.merge(category);

		return categoryDetailConverter.convert(category);

	}

	public CategoryTreeDTO saveCategoryTree(CategoryTreeDTO categoryTreeDTO) {

		Category existingCategory = getCategoryByName(categoryTreeDTO.getName());

		if (categoryTreeDTO.getId() < 1) {
			if (null != existingCategory) {
				throw new CoreServiceException("Category already exist");
			}
		} else if (null != existingCategory
				&& existingCategory.getId() != categoryTreeDTO.getId()) {
			throw new CoreServiceException("This name is not available");
		}

		Category category = categoryTreeConverter.transf(categoryTreeDTO);

		category = em.merge(category);

		return categoryTreeConverter.convert(category);

	}

	public Category getCategoryByName(String name) {

		try {
			TypedQuery<Category> query = em.createNamedQuery(
					"QueryCategoryByName", Category.class);

			query.setParameter("name", name);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
	}

	private Category getCategoryById(long id) {

		return em.find(Category.class, id);
	}

	private List<Category> getTopCategories() {
		TypedQuery<Category> query = em.createNamedQuery("QueryTopCategories",
				Category.class);

		return query.getResultList();
	}

	public CategoryDetailDTO getCategoryDetailDTOById(long id) {
		return categoryDetailConverter.convert(getCategoryById(id));
	}

	public List<CategoryTreeDTO> getCategoryTree(int id) {
		List<CategoryTreeDTO> treeDTOs = new ArrayList<CategoryTreeDTO>();

		if (id < 1) {
			List<Category> categories = getTopCategories();
			if (null != categories) {
				for (Category category : categories) {
					CategoryTreeDTO categoryTreeDTO = categoryTreeConverter
							.convert(category);
					treeDTOs.add(categoryTreeDTO);
				}
			}
		} else {
			Category c = getCategoryById(id);
			if (null != c) {
				List<Category> categories = c.getSubCategory();
				if (null != categories) {
					for (Category category : categories) {
						CategoryTreeDTO categoryTreeDTO = categoryTreeConverter
								.convert(category);
						treeDTOs.add(categoryTreeDTO);
					}
				}
			}
		}

		return treeDTOs;
	}

	public void destory(Category category) {
		category = em.merge(category);
		em.remove(category);
	}

	public void destory(long id) {
		Category category = em.find(Category.class, id);
		em.remove(category);
	}

}
