package com.hb.core.shared.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.hb.core.entity.HTML;
import com.hb.core.entity.Image;
import com.hb.core.entity.Option;
import com.hb.core.entity.OptionItem;
import com.hb.core.entity.Property;
public class ProductDetailDTO {
	
	private long id;
	
	private String keywords;
	
	private String tags;
	
	private String abstractText;
	
	private String detail;
	
	private String title;
	
	private double price;
	
	private double actualPrice;
	
	private String overrideUrl;
	
	private String sku;
	
	@NotNull
	private String name;
	
	private List<Image> images = new ArrayList<Image>();
	
	private List<Option> options = new ArrayList<Option>();
	
	private List<CategoryTreeDTO> categories = new ArrayList<CategoryTreeDTO>();
	 
	private Map<String, HTML> manuals = new HashMap<String,HTML>();
	
	private List<TabProductDTO> relatedProducts = new ArrayList<TabProductDTO>();
	
	private List<Property> props = new ArrayList<Property>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getOverrideUrl() {
		return overrideUrl;
	}

	public void setOverrideUrl(String overrideUrl) {
		this.overrideUrl = overrideUrl;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSku() {
		return sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}


	public Map<String, HTML> getManuals() {
		return manuals;
	}

	public void setManuals(Map<String, HTML> manuals) {
		this.manuals = manuals;
	}

	public List<TabProductDTO> getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(List<TabProductDTO> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	public List<Property> getProps() {
		return props;
	}

	public void setProps(List<Property> props) {
		this.props = props;
	}

	public List<CategoryTreeDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryTreeDTO> categories) {
		this.categories = categories;
	}
	
	public void resetId() {
		this.id = 0;
		/*if (images != null) {
			for (Image image : images) {
				image.setId(0);
			}
		}*/
		if (options != null) {
			for (Option option : options) {
				option.setId(0);
				if(option.getItems() != null) {
					for(OptionItem optionItem : option.getItems()) {
						optionItem.setId(0);
						if(optionItem.getOverrideProps() != null) {
							for(Property property : optionItem.getOverrideProps()) {
								property.setId(0);
							}
						}
					}
				}
			}
		}
		if (props != null) {
			for(Property property : props) {
				property.setId(0);
			}
		}
	}
	
}
