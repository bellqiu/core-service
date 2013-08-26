package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

public class Product extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -84296171648012884L;
	private String keywords;
	private List<String> tags;
	private String abstractText;
	private String detail;
	private int weight;
	private String title;
	private int primaryCategory;
	private List<Long> supportedCategories = new ArrayList<Long>();
	private double price;
	private double actualPrice;
	private String overrideUrl;
	private Image primaryImage;
	private List<Image> secondaryImages = new ArrayList<Image>();
	private List<Option> options = new ArrayList<Option>();
	private List<Property> props = new ArrayList<Property>();
	
	public Product() {
	}
	
	public Image getPrimaryImage() {
		return primaryImage;
	}

	public void setPrimaryImage(Image primaryImage) {
		this.primaryImage = primaryImage;
	}


	public List<Image> getSecondaryImages() {
		return secondaryImages;
	}



	public void setSecondaryImages(List<Image> secondaryImages) {
		this.secondaryImages = secondaryImages;
	}



	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	public int getPrimaryCategory() {
		return primaryCategory;
	}

	public void setPrimaryCategory(int primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public List<Long> getSupportedCategories() {
		return supportedCategories;
	}

	public void setSupportedCategories(List<Long> supportedCategories) {
		this.supportedCategories = supportedCategories;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<Property> getProps() {
		return props;
	}

	public void setProps(List<Property> props) {
		this.props = props;
	}
	
}
