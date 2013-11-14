/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
@Secured("USER")
public class ShoppinController {
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	@RequestMapping("/sp/shoppingcart/list")
	public String shoppingcat( Model model){
		return "shoppingcat";
	}
	 	
	@RequestMapping("/sp/shoppingcart/add/{productId}")
	public String addToCart(@PathVariable("productId") long productId, @RequestParam("option") String optionJson, Model model){
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
