package com.hb.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.CategoryDetailDTO;
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
	
	/*public void destory(Category category){
		Category category =  em.merge(category);
		em.remove(category);
	}*/
	
	public CategoryDetailDTO saveCategoryDetail(CategoryDetailDTO categoryDetailDTO) {
		
		Category existingCategory = getCategoryByName(categoryDetailDTO.getName());
		if (categoryDetailDTO.getId() < 1) {
			if (null != existingCategory) {
				throw new CoreServiceException("Category already exist");
			}
		}else if (null!= existingCategory && existingCategory.getId() != categoryDetailDTO.getId()){
				throw new CoreServiceException("This name is not available");
		}
		
		Category category = catDetail2Cat(categoryDetailDTO);
		
		category = em.merge(category);
		
		return cat2CatDetail(category);
		
	}
	
	private CategoryDetailDTO cat2CatDetail(Category category){
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
		categoryDetailDTO.setParentId(category.getParent()!=null ? category.getParent().getId() : 0);
		return categoryDetailDTO;
	}
	
	
	private Category catDetail2Cat(CategoryDetailDTO categoryDetailDTO){
		Category category = new Category();
		
		if(categoryDetailDTO.getId() > 0){
			category = getCategoryById(categoryDetailDTO.getId());
		}
		
		if(categoryDetailDTO.getParentId() > 0){
			category.setParent(getCategoryById(categoryDetailDTO.getParentId()));
		}else{
			category.setParent(null);
		}
		category.setCreateDate(category.getCreateDate()== null ? new Date() : category.getCreateDate());
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
	
	public CategoryDetailDTO getCategoryDetailDTOById(long id){
		return cat2CatDetail(getCategoryById(id));
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
	
	public void destory(long id) {
		Category category = em.find(Category.class, id);
		em.remove(category);
	}

}
