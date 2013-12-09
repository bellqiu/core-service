package com.hb.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.hb.core.entity.Category.Type;
import com.hb.core.entity.Product;
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
		
		Category existing =  getCategoryByName(category.getName());
		
		if (( null != existing && category.getId() < 1) || ( null != existing && existing.getId() != category.getId()) ) {
			throw new CoreServiceException("Category name already exist");
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
	
	public CategoryDetailDTO getCategoryDetailDTOByName(String name) {
		return categoryDetailConverter.convert(getCategoryByName(name));
	}

	public List<CategoryTreeDTO> getCategoryTree(long id) {
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
	
	private static final Comparator<Category> categoryComparator = new Comparator<Category>(){

		@Override
		public int compare(Category o1, Category o2) {
			if(o1.getPriority() != o2.getPriority()) {
				return o1.getPriority() - o2.getPriority();
			} 
			return o1.getName().compareTo(o2.getName());
		}};
		
	public List<CategoryTreeDTO> getNomalCategoryTree(long id) {
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
					Collections.sort(categories, categoryComparator);
					for (Category category : categories) {
						Type type = category.getType();
						if(Category.Type.NAVIGATION.equals(type) || Category.Type.LINK.equals(type)) {
							CategoryTreeDTO categoryTreeDTO = categoryTreeConverter
									.convert(category);
							treeDTOs.add(categoryTreeDTO);
						}
					}
				}
			}
		}

		return treeDTOs;
	}
	
	public List<CategoryTreeDTO> getSpecialCategoryTree(long id) {
		List<CategoryTreeDTO> treeDTOs = new ArrayList<CategoryTreeDTO>();

		if (id > 0) {
			Category c = getCategoryById(id);
			if (null != c) {
				List<Category> categories = c.getSubCategory();
				if (null != categories) {
					Collections.sort(categories, categoryComparator);
					for (Category category : categories) {
						if(Category.Type.SPECIAL_OFFER.equals(category.getType())) {
							CategoryTreeDTO categoryTreeDTO = categoryTreeConverter
									.convert(category);
							treeDTOs.add(categoryTreeDTO);
						}
					}
				}
			}
		}

		return treeDTOs;
	}

	public void destory(Category category) {
		long id = category.getId();
		String queryString = "select p from Product p, Category c where c.id = :id and c member of p.categories";
		TypedQuery<Product> query = em.createQuery(queryString, Product.class);
		query.setParameter("id", id);
		List<Product> resultList = query.getResultList();
		for(Product p : resultList) {
			for(Category c : p.getCategories()) {
				if(c.getId() == id) {
					p.getCategories().remove(c);
					break;
				}
			}
		}
		category = em.merge(category);
		em.remove(category);
	}

	public void destory(long id) {
		String queryString = "select p from Product p, Category c where c.id = :id and c member of p.categories";
		TypedQuery<Product> query = em.createQuery(queryString, Product.class);
		query.setParameter("id", id);
		List<Product> resultList = query.getResultList();
		for(Product p : resultList) {
			for(Category c : p.getCategories()) {
				if(c.getId() == id) {
					p.getCategories().remove(c);
					break;
				}
			}
		}
		Category category = em.find(Category.class, id);
		em.remove(category);
	}
	
	public List<String> getAllCategoryName() {
		String sqlForAllCategoryName = "select name from Category ";
		TypedQuery<String> cateogryNames = em.createQuery(sqlForAllCategoryName, String.class);
		List<String> allCategoryNames = cateogryNames.getResultList();
		return allCategoryNames;
	}

	public List<String> getCategoryBreadcrumbById(long categoryId) {
		long id = categoryId;
		Category category = getCategoryById(id);
		List<String> categoryBreadcrumbs = new ArrayList<String>();
		while(category != null) {
			categoryBreadcrumbs.add(category.getName());
			category = category.getParent();
		}
		Collections.reverse(categoryBreadcrumbs);
		return categoryBreadcrumbs;
	}

	public List<CategoryTreeDTO> getCategoryTreeByName(List<String> parentNames) {
		if(parentNames == null || parentNames.size() == 0) {
			return getCategoryTree(0);
		}
		List<CategoryTreeDTO> results = new ArrayList<CategoryTreeDTO>();
		for(String name : parentNames) {
			Category category = getCategoryByName(name);
			if(category != null) {
				List<Category> subCategory = category.getSubCategory();
				for(Category sub : subCategory) {
					results.add(categoryTreeConverter.convert(sub));
				}
			}
		}
		return results;
	}
}
