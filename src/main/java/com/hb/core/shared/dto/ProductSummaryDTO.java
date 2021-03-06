package com.hb.core.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public class ProductSummaryDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4356127548130667738L;

	public static final String CAT_SPLIT_IN_BREADCRUMB = ">";
	
	private long id;
	
	private String keywords;
	
	private String tags;
	
	private String abstractText;
	
	private String imageURL;
	
	private String title;
	
	private double price;
	
	private double actualPrice;
	
	private String overrideUrl;
	
	private String sku;
	
	private String name;
	
	private String categoryBreadcrumb;
	
	private Date createDate; 
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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

	public String getCategoryBreadcrumb() {
		return categoryBreadcrumb;
	}

	public void setCategoryBreadcrumb(String categoryBreadcrumb) {
		this.categoryBreadcrumb = categoryBreadcrumb;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	private int like;
	private int sold;
	private int review;

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}
	
}
