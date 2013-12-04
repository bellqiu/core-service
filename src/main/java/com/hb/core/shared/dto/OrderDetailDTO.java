package com.hb.core.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.hb.core.entity.Order.Status;
@JsonAutoDetect
public class OrderDetailDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6451416827762217309L;

	private long id;
	
	private String orderSN;
	
	private String shippingAddress;
	private String billingAddress;
	
	private String trackingId;
	
	private String currency;
	
	private float deliveryPrice;
	
	private String customerMsg;
	
	private String shippingMethod;
	
	private float couponCutOff;
	
	private String traceInfo;
	
	private Status orderStatus;
	
	private String sourceId;
	
	private List<OrderItemDTO> items;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
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

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
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

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
}
