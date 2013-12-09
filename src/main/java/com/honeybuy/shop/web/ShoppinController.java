/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hb.core.entity.Currency;
import com.hb.core.service.OrderService;
import com.honeybuy.shop.util.UserUtils;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.interceptor.SessionAttribute;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class ShoppinController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShoppinController.class);
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/sp/shoppingcart/list")
	public String shoppingcat(Model model){
		return "shoppingcat";
	}
	
	@RequestMapping("/fragment/sp/shoppingcart")
	public String shoppingcatFragment(Model model){
		return "shoppingcatFragment";
	}
	 	
	@RequestMapping("/sp/shoppingcart/add")
	public String addToCart(@RequestParam("productName") String productName, @RequestParam("productOpts") String options, 
							@RequestParam("productAmount") int amount, Model model, 
							@CookieValue(defaultValue="", required=false, value="trackingId")String trackingId,
							@SessionAttribute("defaultCurrency")Currency currency){
		
		logger.info("add to cart productName={}, options={}, amount={}", new Object[]{productName, options, amount});
		
		UserDetails details = UserUtils.getCurrentUser();
		
		orderService.add2Cart(productName, options, trackingId, details == null ? null : details.getUsername(), amount, currency.getCode());
		
		return "redirect:/sp/shoppingcart/list";
	}
	
	@RequestMapping("/sp/shoppingcart/address/{orderId}")
	public String finishOrderInfo(@PathVariable("orderId") long orderId, Model model){
		return "shoppingcatAddress";
	}
	
	@RequestMapping("/sp/shoppingcart/checkout/{orderId}")
	public String cartToCheckout(@PathVariable("orderId") long orderId, Model model){
		return "redirect:/sp/shoppingcart/payment/"+orderId;
	}
	
	@RequestMapping("/sp/shoppingcart/payment/{orderId}")
	public String cartToPay(@PathVariable("orderId") long orderId, Model model){
		return "shoppingcatPayment";
	}
	
}
