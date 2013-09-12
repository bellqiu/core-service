package com.hb.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

import org.hibernate.annotations.IndexColumn;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -84296171648012884L;
	
	@Column(name="keywords")
	private String keywords;
	
	@Column(name="tags")
	private String tags;
	
	@Column(name="abstract_text")
	private String abstractText;
	
	@Column(name="detail")
	private String detail;
	
	@Column(name="title")
	private String title;
	
	@Column(name="price")
	private double price;
	
	@Column(name="actual_price")
	private double actualPrice;
	
	@Column(name="override_url")
	private String overrideUrl;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Product_has_Images",
				joinColumns=@JoinColumn(name="Product_id"),
				inverseJoinColumns=@JoinColumn(name="Image_id"))
	@IndexColumn(name="sequence", base=0, nullable=false)
	private List<Image> images = new ArrayList<Image>();
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Product_has_Option",
				joinColumns=@JoinColumn(name="Product_id"),
				inverseJoinColumns=@JoinColumn(name="Option_id"))
	@IndexColumn(name="sequence", base=0, nullable=false)
	private List<Option> options = new ArrayList<Option>();
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Product_has_Property",
				joinColumns=@JoinColumn(name="Product_id"),
				inverseJoinColumns=@JoinColumn(name="Property_id"))
	@IndexColumn(name="sequence", base=0, nullable=false)
	private List<Property> props = new ArrayList<Property>();
	
	@ManyToMany
	@JoinTable(name="Product_has_TabProduct",
				joinColumns=@JoinColumn(name="Product_id"),
				inverseJoinColumns=@JoinColumn(name="TabProduct_id"))
	@IndexColumn(name="sequence", base=0, nullable=false)
	private List<TabProduct> product = new ArrayList<TabProduct>();
	
	@ManyToMany
	@JoinTable(name="Product_has_Category",
				joinColumns=@JoinColumn(name="Product_id"),
				inverseJoinColumns=@JoinColumn(name="Category_id"))
	@IndexColumn(name="sequence", base=0, nullable=false)
	private List<Category> categories = new ArrayList<Category>();
	
	@ManyToMany()
	@JoinTable(name="Product_has_Mannual",
				joinColumns=@JoinColumn(name="Product_id"),
				inverseJoinColumns=@JoinColumn(name="HTML_id"))
	@MapKeyColumn(name="name")
	private Map<String, HTML> manuals = new HashMap<String, HTML>();
	
	
	public Product() {
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
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

	public List<Property> getProps() {
		return props;
	}

	public void setProps(List<Property> props) {
		this.props = props;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TabProduct> getProduct() {
		return product;
	}

	public void setProduct(List<TabProduct> product) {
		this.product = product;
	}

	public Map<String, HTML> getManuals() {
		return manuals;
	}

	public void setManuals(Map<String, HTML> manuals) {
		this.manuals = manuals;
	}
	
}
