package com.hb.core.service;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.shared.dto.OrderDetailDTO;


@Transactional
@Service
public class OrderService {
	
	@PersistenceContext
	private EntityManager em;
	
	public OrderDetailDTO add2Cart(long productId, Map<String,String> opts, String trackingId, String userEmail, int quantity){
		return null;
	}
	
	public OrderDetailDTO modifyCart(long productId, String trackingId, String userEmail,  int quantity){
		return null;
	}
	
	public OrderDetailDTO getCart(String trackingId, String userEmail){
		return null;
	}
	
	public OrderDetailDTO getOrder(String OrderSN){
		return null;
	}
	
	public OrderDetailDTO applyCoupon(String OrderSN, String couponCode){
		return null;
	}
	
	public OrderDetailDTO checkout(String OrderSN, String userEmail, String billingAddress, String shippingAddress, String shippingCountryCode, String extMessage){
		return null;
	}
}
