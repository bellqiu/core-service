package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.IndexColumn;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries(value=
	{
		@NamedQuery(name="QueryTabProductByName", query="select tp from TabProduct as tp where name=:name"),
		@NamedQuery(name="countAllTabProduct", query="select count(tp.id) from TabProduct as tp"),
	})
public class TabProduct extends Component{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2574127222646178290L;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="TabProduct_has_Product",
				joinColumns=@JoinColumn(name="TabProduct_id"),
				inverseJoinColumns=@JoinColumn(name="Product_id"))
	@IndexColumn(name="sequence", base=0, nullable=false)
	private List<Product> products = new ArrayList<Product>();
	
	@Column(name="name")
	private String name;
	
	public TabProduct() {
	}

	public List<Product>  getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
