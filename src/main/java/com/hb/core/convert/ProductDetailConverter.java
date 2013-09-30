package com.hb.core.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.entity.Category;
import com.hb.core.entity.HTML;
import com.hb.core.entity.Image;
import com.hb.core.entity.Option;
import com.hb.core.entity.Product;
import com.hb.core.entity.Property;
import com.hb.core.entity.TabProduct;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.TabProductDTO;

@org.springframework.stereotype.Component
public class ProductDetailConverter implements Converter<ProductDetailDTO, Product>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Converter<CategoryTreeDTO, Category> categoryTreeConverter;
	
	@Autowired
	private Converter<TabProductDTO, TabProduct> tabProductConverter;
	
	@Override
	public ProductDetailDTO convert(Product product) {
		
		if(null == product){
			return null;
		}
		
		
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
		
		detailDTO.setManuals(product.getManuals());
		
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
		
		if(null == productDetailDTO){
			return null;
		}
		
		
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
		
		product.setCategories(new ArrayList<Category>());
		
		if(null != productDetailDTO.getCategories()){
			for (CategoryTreeDTO c : productDetailDTO.getCategories()) {
				product.getCategories().add(categoryTreeConverter.transf(c));
			}
		}
		
		product.setManuals(new HashMap<String, HTML>());
		if(null != productDetailDTO.getManuals()){
			for (Map.Entry<String, HTML> c : productDetailDTO.getManuals().entrySet()) {
				product.getManuals().put(c.getKey(), em.find(HTML.class, c.getValue().getId()));
			}
		}
		
		product.setImages(new ArrayList<Image>());
		if(null != productDetailDTO.getImages()){
			for (Image image : productDetailDTO.getImages()) {
				product.getImages().add(em.find(Image.class, image.getId()));
			}
		}
		
		product.setOptions(new ArrayList<Option>());
		if(null != productDetailDTO.getOptions()){
			for (Option option : productDetailDTO.getOptions()) {
				product.getOptions().add(em.find(Option.class, option.getId()));
			}
		}
		
		product.setProps(new ArrayList<Property>());
		if(null != productDetailDTO.getProps()){
			for (Property property : productDetailDTO.getProps()) {
				product.getProps().add(em.find(Property.class, property.getId()));
			}
		}
		
		product.setRelatedProducts(new ArrayList<TabProduct>());
		if(null != productDetailDTO.getRelatedProducts()){
			for (TabProductDTO tabProductDTO : productDetailDTO.getRelatedProducts()) {
				product.getRelatedProducts().add(tabProductConverter.transf(tabProductDTO));
			}
		}
		
		
		return product;
	}
	
}
