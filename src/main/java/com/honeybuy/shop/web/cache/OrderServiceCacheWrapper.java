package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.OrderService;

@Service
@Transactional(readOnly=true)
public class OrderServiceCacheWrapper {
	@Autowired
	private OrderService orderService;
	
	@Cacheable(cacheName="cartItemCount")
	public int getCartItemCount(String trackingId, String email){
		return orderService.getCartItemCount(trackingId, email);
	}
}
