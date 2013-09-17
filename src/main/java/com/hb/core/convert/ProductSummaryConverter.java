package com.hb.core.convert;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.entity.Product;
import com.hb.core.shared.dto.ProductSummaryDTO;

@Service
@Transactional(readOnly=true)
public class ProductSummaryConverter implements Converter<ProductSummaryDTO, Product>{
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
	public ProductSummaryDTO convert(Product p) {
		
		if(null == p){
			return null;
		}
		
		
		ProductSummaryDTO summaryDTO = new ProductSummaryDTO();
		summaryDTO.setAbstractText(p.getAbstractText());
		summaryDTO.setActualPrice(p.getActualPrice());
		
		String categoryBreadcrumb = "";
		if(null != p.getCategories()){
			for (Iterator<Category> iterator = p.getCategories().iterator(); iterator.hasNext();) {
				Category c = iterator.next();
				categoryBreadcrumb += c.getDisplayName();
				if(iterator.hasNext()){
					categoryBreadcrumb += ProductSummaryDTO.CAT_SPLIT_IN_BREADCRUMB;
				}
			}
		}
		summaryDTO.setCategoryBreadcrumb(categoryBreadcrumb);
		
		summaryDTO.setId(p.getId());
		summaryDTO.setKeywords(p.getKeywords());
		summaryDTO.setName(p.getName());
		summaryDTO.setOverrideUrl(p.getOverrideUrl());
		summaryDTO.setPrice(p.getPrice());
		summaryDTO.setTags(p.getTags());
		summaryDTO.setTitle(p.getTitle());
		
		return summaryDTO;
	}

	@Override
	public Product transf(ProductSummaryDTO pto) {
		
		if(null == pto){
			return null;
		}
		
		
		Product product  = new Product();
		
		if(pto.getId() > 0){
			product = em.find(Product.class, pto.getId());
			
		}
		
		product.setAbstractText(pto.getAbstractText());
		product.setActualPrice(pto.getActualPrice());
		product.setId(pto.getId());
		product.setKeywords(pto.getKeywords());
		product.setName(pto.getName());
		product.setOverrideUrl(pto.getOverrideUrl());
		product.setPrice(pto.getPrice());
		product.setTags(pto.getTags());
		product.setTitle(pto.getTitle());
		
		return product;
	}

}
