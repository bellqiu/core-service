package com.hb.core.shared.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class OrderItemDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1713501806086077572L;

	private int quantity;
	
	private float finalPrice;
	
	private ProductSummaryDTO productSummary;
	
	private Map<String, Float> selectedOpts;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}

	public ProductSummaryDTO getProductSummary() {
		return productSummary;
	}

	public void setProductSummary(ProductSummaryDTO productSummary) {
		this.productSummary = productSummary;
	}

	public Map<String, Float> getSelectedOpts() {
		return selectedOpts;
	}

	public void setSelectedOpts(Map<String, Float> selectedOpts) {
		this.selectedOpts = selectedOpts;
	}

}
