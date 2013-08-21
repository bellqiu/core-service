package com.hb.core.entity;

import java.util.List;


public class OrderItem extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6303793659582817131L;

	private long productId;
	private int quantity;
	private float finalPrice;
	private List<String> selectedOpts;
	
	public OrderItem() {
	}

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

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public List<String> getSelectedOpts() {
		return selectedOpts;
	}

	public void setSelectedOpts(List<String> selectedOpts) {
		this.selectedOpts = selectedOpts;
	}
	
}
