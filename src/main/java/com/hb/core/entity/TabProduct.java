package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

public class TabProduct extends Component{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2574127222646178290L;
	private List<Long> products = new ArrayList<Long>();
	private String name;
	
	public TabProduct() {
	}

	public List<Long> getProducts() {
		return products;
	}

	public void setProducts(List<Long> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
