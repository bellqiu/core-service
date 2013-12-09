package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OrderItem extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6303793659582817131L;

	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="Product_id")
	private Product product;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="final_price")
	private float finalPrice;
	
	@OneToMany(targetEntity=SelectedOpts.class,cascade={CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="OrderItem_id")
	private List<SelectedOpts> selectedOpts = new ArrayList<SelectedOpts>();
	
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

	public List<SelectedOpts> getSelectedOpts() {
		return selectedOpts;
	}

	public void setSelectedOpts(List<SelectedOpts> selectedOpts) {
		this.selectedOpts = selectedOpts;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(finalPrice);
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
		result = prime * result
				+ ((selectedOpts == null) ? 0 : selectedOpts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (Float.floatToIntBits(finalPrice) != Float
				.floatToIntBits(other.finalPrice))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		if (selectedOpts == null) {
			if (other.selectedOpts != null)
				return false;
		} else if (!selectedOpts.equals(other.selectedOpts))
			return false;
		return true;
	}
	
}
