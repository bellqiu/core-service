package com.hb.core.convert;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.entity.Product;
import com.hb.core.entity.TabProduct;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;

@Service
@Transactional(readOnly=true)
public class ProductDetailConverter implements Converter<ProductDetailDTO, Product>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<CategoryTreeDTO, Category> categoryTreeConverter;
	
	@Autowired
	private Converter<ProductSummaryDTO, Product> productSummaryConverter;
	
	@Override
	public ProductDetailDTO convert(Product product) {
		ProductDetailDTO detailDTO = new ProductDetailDTO();
		detailDTO.setAbstractText(product.getAbstractText());
		detailDTO.setActualPrice(product.getActualPrice());
		
		//transfer categories
		List<Category> categories = product.getCategories();
		if(null != categories){
			for (Category category : categories) {
				detailDTO.getCategories().add(categoryTreeConverter.convert(category));
			}
		}
		//end transfer categories
		detailDTO.setDetail(product.getDetail());
		detailDTO.setId(product.getId());
		
		detailDTO.setImages(product.getImages());
		
		detailDTO.setKeywords(product.getKeywords());
		
		detailDTO.setMannuals(product.getManuals());
		
		detailDTO.setName(product.getName());
		
		detailDTO.setOptions(product.getOptions());
		
		detailDTO.setOverrideUrl(product.getOverrideUrl());
		
		detailDTO.setPrice(product.getPrice());
		
		detailDTO.setProps(product.getProps());
		
		if(product.getRelatedProducts()!= null){
			for (TabProduct tp : product.getRelatedProducts()) {
				for (Product p : tp.getProducts()) {
					ProductSummaryDTO dto = productSummaryConverter.convert(p);
					detailDTO.getRelatedProducts().add(dto);
				}
			}
		}
		
		detailDTO.setTags(product.getTags());
		
		detailDTO.setTitle(product.getTitle());
		
		return detailDTO;
	}

	@Override
	public Product transf(ProductDetailDTO productDetailDTO) {
		return null;
	}
	
}
