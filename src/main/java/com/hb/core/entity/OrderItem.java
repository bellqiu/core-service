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
	
}
