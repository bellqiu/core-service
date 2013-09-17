package com.hb.core.convert;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Category;
import com.hb.core.entity.HTML;
import com.hb.core.entity.Image;
import com.hb.core.entity.Option;
import com.hb.core.entity.Product;
import com.hb.core.entity.Property;
import com.hb.core.entity.TabProduct;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.shared.dto.TabProductDTO;

@Service
@Transactional(readOnly=true)
public class ProductDetailConverter implements Converter<ProductDetailDTO, Product>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<CategoryTreeDTO, Category> categoryTreeConverter;
	
	@Autowired
	private Converter<ProductSummaryDTO, Product> productSummaryConverter;
	
	@Autowired
	private Converter<TabProductDTO, TabProduct> tabProductConverter;
	
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
		
		detailDTO.setId(product.getId());
		
		detailDTO.setDetail(product.getDetail());
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
				detailDTO.getRelatedProducts().add(tabProductConverter.convert(tp));
			}
		}
		
		detailDTO.setTags(product.getTags());
		
		detailDTO.setTitle(product.getTitle());
		
		return detailDTO;
	}

	@Override
	public Product transf(ProductDetailDTO productDetailDTO) {
		
		Product product = new Product(); 
		
		if(productDetailDTO.getId() > 0){
			product = em.find(Product.class, productDetailDTO.getId());
		}
		
		
		product.setAbstractText(productDetailDTO.getAbstractText());
		product.setActualPrice(productDetailDTO.getActualPrice());
		
		product.setDetail(productDetailDTO.getDetail());
		
		product.setKeywords(productDetailDTO.getKeywords());
		
		//product.setMannuals(product.getManuals());
		
		product.setName(productDetailDTO.getName());
		
		product.setOverrideUrl(productDetailDTO.getOverrideUrl());
		
		product.setPrice(productDetailDTO.getPrice());
		
		//detailDTO.setProps(product.getProps());
		
		product.setTags(productDetailDTO.getTags());
		
		product.setTitle(productDetailDTO.getTitle());
		
		if(null != productDetailDTO.getCategories()){
			for (CategoryTreeDTO c : productDetailDTO.getCategories()) {
				product.getCategories().add(categoryTreeConverter.transf(c));
			}
		}
		
		if(null != productDetailDTO.getMannuals()){
			for (Map.Entry<String, HTML> c : productDetailDTO.getMannuals().entrySet()) {
				product.getManuals().put(c.getKey(), em.find(HTML.class, c.getValue().getId()));
			}
		}
		
		if(null != productDetailDTO.getImages()){
			for (Image image : productDetailDTO.getImages()) {
				product.getImages().add(em.find(Image.class, image.getId()));
			}
		}
		
		if(null != productDetailDTO.getOptions()){
			for (Option option : productDetailDTO.getOptions()) {
				product.getOptions().add(em.find(Option.class, option.getId()));
			}
		}
		
		if(null != productDetailDTO.getProps()){
			for (Property property : productDetailDTO.getProps()) {
				product.getProps().add(em.find(Property.class, property.getId()));
			}
		}
		
		if(null != productDetailDTO.getRelatedProducts()){
			for (TabProductDTO tabProductDTO : productDetailDTO.getRelatedProducts()) {
				product.getRelatedProducts().add(tabProductConverter.transf(tabProductDTO));
			}
		}
		
		
		return product;
	}
	
}
