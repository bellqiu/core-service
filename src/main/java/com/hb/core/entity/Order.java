package com.hb.core.entity;

import java.util.List;

public class Order extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8785205423133606769L;
	
	private String shippingAddress;
	private String billingAddress;
	
	private long userId;
	
	private List<OrderItem> items;
	
	private String currency;
	
	private float deliveryPrice;
	
	private String customerMsg;
	
	private String shippingMethod;
	
	private float couponCutOff;
	
	private String couponCode;
	
	private String traceInfo;
	
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
