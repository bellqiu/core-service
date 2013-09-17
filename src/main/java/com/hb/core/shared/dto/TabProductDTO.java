package com.hb.core.shared.dto;

import java.util.ArrayList;
import java.util.List;

public class TabProductDTO {
	private long id;
	private String name;
	
	private List<ProductSummaryDTO> products = new ArrayList<ProductSummaryDTO>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductSummaryDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductSummaryDTO> products) {
		this.products = products;
	}
	
	
}
