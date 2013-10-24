package com.hb.core.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonAutoDetect

@NamedQueries(value=
{
	@NamedQuery(name="QueryCategoryByName", query="select c from Category as c where name=:name "),
	@NamedQuery(name="QueryTopCategories", query="select c from Category as c where parent is null order by priority, name ")
})
public class Category  extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754872499051739634L;
	@Column(name="display_Name")
	private String displayName;
	
	@Column(name="icon_Url")
	private String iconUrl;
	
	@Column(name="url")
	private String url;
	
	@Column(name="related_keyword")
	private String relatedKeyword;

	@Column(name="page_title")
	private String pageTitle;
	
	@Column(name="market_content")
	private String marketContent;
	
	@Column(name="description")
	private String description;
	
	@Column(name="name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type = Type.NAVIGATION;
	
	@Column(name = "priority")
	private int priority;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="parent_id")
	private Category parent;
	
	@OneToMany(mappedBy="parent", orphanRemoval=true)
	private List<Category> subCategory;
	
	public static enum Type{
		LINK,
		SPECIAL_OFFER,
		ICON,
		NAVIGATION
	}
	
	public Category() {
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRelatedKeyword() {
		return relatedKeyword;
	}

	public void setRelatedKeyword(String relatedKeyword) {
		this.relatedKeyword = relatedKeyword;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getMarketContent() {
		return marketContent;
	}

	public void setMarketContent(String marketContent) {
		this.marketContent = marketContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<Category> subCategory) {
		this.subCategory = subCategory;
	}
	
	

}
