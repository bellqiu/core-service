package com.hb.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="HB_Order")
@NamedQueries(value=
{
	@NamedQuery(name="QueryOnShoppingOrderByTrackingId", query="select o from Order as o where o.trackingId=:trackingId and o.user is null and o.orderStatus='ONSHOPPING'"),
	@NamedQuery(name="QueryOnShoppingOrderByUserEmail", query="select o from Order as o where o.user.email=:email and o.orderStatus='ONSHOPPING'"),
	@NamedQuery(name="QueryOnUserOrderByUsername", query="select o from Order as o where o.user.email=:email and o.orderStatus!='ONSHOPPING' order by o.updateDate desc"),
	@NamedQuery(name="QueryOnUserOrderCountByUsername", query="select count(o.id) from Order as o where o.user.email=:email and o.orderStatus!='ONSHOPPING'"),
	@NamedQuery(name="QueryOrderBySN", query="select o from Order as o where o.orderSN=:orderSN")
})
public class Order extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8785205423133606769L;
	
	@Column(name="shipping_address")
	private String shippingAddress;
	
	@Column(name="billing_address")
	private String billingAddress;
	
	@Column(name="shipping_add_ref")
	private long shippingAddRef;
	
	@Column(name="billing_add_ref")
	private long billingAddRef;
	
	@ManyToOne(optional=true, cascade={CascadeType.REFRESH})
	@JoinColumn(name="User_id")
	private User user;
	
	@Column(name="shippingCode")
	private String shippingCode;
	
	@Column(name="orderSN")
	private String orderSN;
	
	@Column(name="tacking_id")
	private String trackingId;
	
	@Column(name="source_Id")
	private String sourceId;
	
	@OneToMany(targetEntity=OrderItem.class,orphanRemoval=true, cascade={CascadeType.ALL})
	@JoinColumn(name="Order_id")
	private List<OrderItem> items = new ArrayList<OrderItem>();
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="delivery_price")
	private float deliveryPrice;
	
	@Column(name="customer_msg")
	private String customerMsg;
	
	@Column(name="shipping_method")
	private String shippingMethod;
	
	@Column(name="coupon_cut_off")
	private float couponCutOff;
	
	@Column(name="coupon_code")
	private String couponCode;
	
	@Column(name="trace_info")
	private String traceInfo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="order_status")
	private Status orderStatus = Status.ONSHOPPING; 
	
	public static enum Status{
		ONSHOPPING,
		PENDING,
		CANCELED,
		PAID,
		SHIPPING,
		COMPLETED
	}


	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(float deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public String getCustomerMsg() {
		return customerMsg;
	}

	public void setCustomerMsg(String customerMsg) {
		this.customerMsg = customerMsg;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public float getCouponCutOff() {
		return couponCutOff;
	}

	public void setCouponCutOff(float couponCutOff) {
		this.couponCutOff = couponCutOff;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getTraceInfo() {
		return traceInfo;
	}

	public void setTraceInfo(String traceInfo) {
		this.traceInfo = traceInfo;
	}

	public Status getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Status orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public long getBillingAddRef() {
		return billingAddRef;
	}

	public void setBillingAddRef(long billingAddRef) {
		this.billingAddRef = billingAddRef;
	}

	public long getShippingAddRef() {
		return shippingAddRef;
	}

	public void setShippingAddRef(long shippingAddRef) {
		this.shippingAddRef = shippingAddRef;
	}
	
	public float getTotalProductPrice(){
		float productPrice = 0;
		
		for (OrderItem item : getItems()) {
			productPrice = productPrice + (item.getFinalPrice() * item.getQuantity());
		}
		
		return productPrice;
	}
	
	public float getAmount() {
		return getTotalProductPrice() + getDeliveryPrice() - getCouponCutOff();
	}

}
