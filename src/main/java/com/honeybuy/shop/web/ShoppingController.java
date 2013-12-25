/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Address;
import com.hb.core.entity.Currency;
import com.hb.core.service.OrderService;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.interceptor.SessionAttribute;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class ShoppingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/sp/shoppingcart/list")
	public String shoppingcat(Model model){
		return "shoppingcat";
	}
	
	@RequestMapping("/sp/shoppingcart/itemCount")
	@ResponseBody
	public int getCartItemCount(Model model, @CookieValue(defaultValue="", required=false, value=Constants.TRACKING_COOKE_ID_NAME)String trackingId, @SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		String useremail = null;
		if(null != details){
			useremail = details.getUsername();
		}
		return orderService.getCartItemCount(trackingId, useremail);
	}
	
	@RequestMapping("/fragment/sp/shoppingcart")
	public String shoppingcatFragment(Model model, @CookieValue(defaultValue="", required=false, value=Constants.TRACKING_COOKE_ID_NAME)String trackingId,  @SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		
		String useremail = null;
		if(null != details){
			useremail = details.getUsername();
		}
		
		OrderDetailDTO detailDTO = orderService.getCart(trackingId, useremail);
		if(null != detailDTO){
			model.addAttribute("currentOrder", detailDTO);
		}
		
		return "shoppingcartFragment";
	}
	
	@RequestMapping("/fragment/sp/shoppingcart/modify")
	public String modifyCart(Model model, @CookieValue(defaultValue="", required=false, value=Constants.TRACKING_COOKE_ID_NAME)String trackingId, @RequestParam("itemId")String itemId, @RequestParam("changes")String changes,  @SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		
		String useremail = null;
		if(null != details){
			useremail = details.getUsername();
		}
		
		orderService.modifyCart(trackingId, useremail, itemId, changes);
		
		return "redirect:/fragment/sp/shoppingcart";
	}
	 	
	@RequestMapping("/sp/shoppingcart/add")
	public String addToCart(@RequestParam("productName") String productName, @RequestParam("productOpts") String options, 
							@RequestParam("productAmount") int amount, Model model, 
							@CookieValue(defaultValue="", required=false, value=Constants.TRACKING_COOKE_ID_NAME)String trackingId,
							@SessionAttribute("defaultCurrency")Currency currency,
							 @SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		
		logger.info("add to cart productName={}, options={}, amount={}", new Object[]{productName, options, amount});
		
		
		orderService.add2Cart(productName, options, trackingId, details == null ? null : details.getUsername(), amount, currency.getCode());
		
		return "redirect:/sp/shoppingcart/list";
	}
	
	@Secured("USER")
	@RequestMapping("/sp/payment/paymentInfo")
	public String finishOrderInfo(@RequestParam(value="orderId", required=false, defaultValue="0") long orderId, Model model, 
			 @SessionAttribute(required=false, value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details){
		
		String useremail = null;
		if(null != details){
			useremail = details.getUsername();
		}
		
		List<Address> addresses = userService.getUserAddresses(useremail);
		
		OrderDetailDTO orderDetailDTO  = null;
		
	
		if(orderId > 0){
			orderDetailDTO = orderService.getOrderDetailById(orderId);
		}else{
			orderDetailDTO = orderService.getCart(null, useremail);
		}
		
		if(null != addresses && null!=orderDetailDTO){
			
			long shippingAddIdRef = orderDetailDTO.getShippingAddressRef();
			long billingAddIdRef = orderDetailDTO.getBillingAddressRef();
			
			Address shippingAdd = null;
			Address billingAdd = null;
			
			if(shippingAddIdRef > 0){
				shippingAdd = userService.getUserAddressById(shippingAddIdRef, useremail);
				
				if(null == shippingAdd){
					orderDetailDTO = orderService.updateOrderShippingAddress(addresses.get(0), orderId);
				}
			}
			
			if(billingAddIdRef > 0){
				billingAdd = userService.getUserAddressById(shippingAddIdRef, useremail);
				if(null == billingAdd){
					orderDetailDTO = orderService.updateOrderShippingAddress(addresses.get(0), orderId);
				}
			}
			
			model.addAttribute("addresses", addresses);
		}
		
		
		if(null != orderDetailDTO && useremail.equals(orderDetailDTO.getUseremail())){
			model.addAttribute("currentOrder", orderDetailDTO);
		}
		
		return "shoppingcatAddress";
	}
	
	
	@RequestMapping("/sp/payment/checkout/{orderId}")
	public String cartToCheckout(@PathVariable("orderId") long orderId, Model model){
		return "redirect:/sp/shoppingcart/payment/"+orderId;
	}
	
	@RequestMapping("/sp/payment/payment/{orderId}")
	public String cartToPay(@PathVariable("orderId") long orderId, Model model){
		return "shoppingcatPayment";
	}
	
}
