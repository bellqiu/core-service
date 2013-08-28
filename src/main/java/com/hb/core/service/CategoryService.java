package com.hb.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.CategoryTreeDTO;

@Transactional
@Service
public class CategoryService {

	@PersistenceContext
	private EntityManager em;

	public Category saveOrUpdate(Category category) {
		if (category.getId() < 1) {
			if (null != getCategoryByName(category.getName())) {
				throw new CoreServiceException("Category already exist");
			}
		}
		category = em.merge(category);
		return category;
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
		TypedQuery<Category> query = em.createNamedQuery(
				"QueryTopCategories", Category.class);
		
		return query.getResultList();
	}
	
	public List<CategoryTreeDTO> getCategoryTree(int id){
		List<CategoryTreeDTO> treeDTOs = new ArrayList<CategoryTreeDTO>();
		
		if(id < 1){
			List<Category> categories = getTopCategories();
			if(null != categories){
				for (Category category : categories) {
					CategoryTreeDTO categoryTreeDTO  = new CategoryTreeDTO();
					categoryTreeDTO.setDisplayName(category.getDisplayName());
					categoryTreeDTO.setId(category.getId());
					categoryTreeDTO.setLeaf(category.getSubCategory() == null || category.getSubCategory().size() < 1 );
					categoryTreeDTO.setName(category.getName());
					categoryTreeDTO.setType(category.getType());
					treeDTOs.add(categoryTreeDTO);
				}
			}
		}else{
			Category c = getCategoryById(id);
			if(null != c){
				List<Category> categories = c.getSubCategory();
				if(null != categories){
					for (Category category : categories) {
						CategoryTreeDTO categoryTreeDTO  = new CategoryTreeDTO();
						categoryTreeDTO.setDisplayName(category.getDisplayName());
						categoryTreeDTO.setId(category.getId());
						categoryTreeDTO.setLeaf(category.getSubCategory() == null || category.getSubCategory().size() < 1 );
						categoryTreeDTO.setName(category.getName());
						categoryTreeDTO.setType(category.getType());
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

}
