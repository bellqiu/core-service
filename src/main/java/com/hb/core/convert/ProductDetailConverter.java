package com.hb.core.convert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.entity.Category;
import com.hb.core.entity.Component;
import com.hb.core.entity.HTML;
import com.hb.core.entity.Image;
import com.hb.core.entity.Option;
import com.hb.core.entity.OptionItem;
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
		
		detailDTO.setSku(product.getSku());
		
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
		
		product.setSku(productDetailDTO.getSku());
		
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
				Option existingOption = em.find(Option.class, option.getId());
				if(existingOption != null) {
					existingOption.setName(option.getName());
					existingOption.setType(option.getType() == null ? Option.Type.TEXT : option.getType());
					existingOption.setDefaultValue(option.getDefaultValue());
					existingOption.setDesc(option.getDesc());
					List<OptionItem> items = option.getItems();
					if(items != null && items.size() > 0) {
						List<OptionItem> existingOptionItems = existingOption.getItems();
						Map<Long, OptionItem> itemMap = new HashMap<Long, OptionItem>();
						for(OptionItem optionItem : existingOptionItems) {
							itemMap.put(optionItem.getId(), optionItem);
						}
						Set<Long> existingIdSet = itemMap.keySet();
						for(OptionItem optionItem : items) {
							if(existingIdSet.contains(optionItem.getId())) {
								OptionItem existingOptionItem = itemMap.get(optionItem.getId());
								existingOptionItem.setDisplayName(optionItem.getDisplayName());
								existingOptionItem.setIconUrl(optionItem.getIconUrl());
								existingOptionItem.setPriceChange(optionItem.getPriceChange());
								existingOptionItem.setValue(optionItem.getValue());
								existingOptionItem.setUpdateDate(new Date());
								List<Property> overrideProps = optionItem.getOverrideProps();
								if(overrideProps != null && overrideProps.size() > 0) {
									List<Property> existingOverridePropsList = existingOptionItem.getOverrideProps();
									Map<Long, Property> propertyMap = new HashMap<Long, Property>();
									for(Property property : existingOverridePropsList) {
										propertyMap.put(property.getId(), property);
									}
									Set<Long> existingPropertyIdSet = propertyMap.keySet();
									for(Property property : overrideProps) {
										if(existingPropertyIdSet.contains(property.getId())) {
											Property existingProperty = propertyMap.get(property.getId());
											existingProperty.setName(property.getName());
											existingProperty.setValue(property.getValue());
											existingProperty.setDesc(property.getDesc());
											existingProperty.setUpdateDate(new Date());
											existingPropertyIdSet.remove(property.getId());
										} else {
											property.setId(0);
											property.setCreateDate(new Date());
											property.setUpdateDate(new Date());
											existingOverridePropsList.add(property);
										}
									}
									if(existingPropertyIdSet.size() > 0) {
										for(Long propertyId : existingPropertyIdSet) {
											existingOverridePropsList.remove(propertyMap.get(propertyId));
										}
									}
									existingOptionItem.setOverrideProps(existingOverridePropsList);
								}
								existingIdSet.remove(optionItem.getId());
							} else {
								optionItem.setId(0);
								optionItem.setCreateDate(new Date());
								optionItem.setUpdateDate(new Date());
								existingOptionItems.add(optionItem);
							}
						}
						if(existingIdSet.size() > 0) {
							for(Long optionItemId : existingIdSet) {
								existingOptionItems.remove(itemMap.get(optionItemId));
							}
						}
						existingOption.setItems(existingOptionItems);
					}
				} else {
					existingOption = option;
					existingOption.setCreateDate(new Date());
				}
				existingOption.setUpdateDate(new Date());
				product.getOptions().add(existingOption);
			}
		}
		
		product.setProps(new ArrayList<Property>());
		if(null != productDetailDTO.getProps()){
			for (Property property : productDetailDTO.getProps()) {
				Property existingProperty = em.find(Property.class, property.getId());
				if(existingProperty != null) {
					existingProperty.setName(property.getName());
					existingProperty.setValue(property.getValue());
					existingProperty.setDesc(property.getDesc());
					existingProperty.setStatus(property.getStatus() == null ? Component.Status.ACTIVE : property.getStatus());
				} else {
					existingProperty = property;
					existingProperty.setCreateDate(new Date());
				}
				existingProperty.setUpdateDate(new Date());
				product.getProps().add(existingProperty);
				//em.merge(existingProperty);
			}
		}
		
		product.setRelatedProducts(new ArrayList<TabProduct>());
		if(null != productDetailDTO.getRelatedProducts()){
			for (TabProductDTO tabProductDTO : productDetailDTO.getRelatedProducts()) {
				TabProduct tabProduct = em.find(TabProduct.class, tabProductDTO.getId());
				if(tabProduct != null) {
					product.getRelatedProducts().add(tabProduct);
				} else {
					product.getRelatedProducts().add(tabProductConverter.transf(tabProductDTO));
				}
			}
		}
		
		return product;
	}
	
}
