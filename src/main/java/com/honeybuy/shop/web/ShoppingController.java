/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hb.core.entity.Coupon;
import com.hb.core.entity.Currency;
import com.hb.core.service.CouponService;
import com.hb.core.service.OrderService;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.dto.ResponseResult;
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
	
	@Autowired
	private CouponService couponService;
	
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
			orderId = orderDetailDTO.getId();
		}
		
		if(null != addresses && !addresses.isEmpty() && null!=orderDetailDTO){
			
			long shippingAddIdRef = orderDetailDTO.getShippingAddressRef();
			long billingAddIdRef = orderDetailDTO.getBillingAddressRef();
			
			Address shippingAdd = null;
			Address billingAdd = null;
			
			if(shippingAddIdRef > 0){
				shippingAdd = userService.getUserAddressById(shippingAddIdRef, useremail);
			}
			
			if(null == shippingAdd){
				orderDetailDTO = orderService.updateOrderShippingAddress(addresses.get(0), orderId);
			}
			
			if(billingAddIdRef > 0){
				billingAdd = userService.getUserAddressById(billingAddIdRef, useremail);
			}else{
				billingAdd = userService.getUserAddressById(shippingAddIdRef, useremail);
			}
			
			if(null == billingAdd){
				orderDetailDTO = orderService.updateOrderShippingAddress(addresses.get(0), orderId);
			}
			
			model.addAttribute("addresses", addresses);
		}
		
		
		if(null != orderDetailDTO && useremail.equals(orderDetailDTO.getUseremail())){
			model.addAttribute("currentOrder", orderDetailDTO);
		}
		
		model.addAttribute("normalDeliverPrice", orderService.getDeliverPrice(orderDetailDTO.getShippingCode()));
		model.addAttribute("expeditedDeliverPrice", orderService.getExpeditedDeliverPrice(orderDetailDTO.getShippingCode()));
		
		return "shoppingcatAddress";
	}
	
	@Secured("USER")
	@RequestMapping("/sp/payment/updateOrderAdd")
	public ResponseResult<OrderDetailDTO> updateOrderAddress(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 @RequestParam("addressId")long addressId,
			 @RequestParam("addType")String addType
			 ){
		
		OrderDetailDTO detailDTO = null;
		Address address = userService.getUserAddressById(addressId, details.getUsername());
		
		if("SHIPPING".equals(addType)){
			detailDTO = orderService.updateOrderShippingAddress(address, orderId);
		}else{
			detailDTO = orderService.updateOrderBillingAddress(address, orderId);
		}
		
		ResponseResult<OrderDetailDTO> result = new ResponseResult<OrderDetailDTO>(true, detailDTO);
		
		if(detailDTO == null){
			result.setSuccess(false);
		}else{
			result.getMessageMap().putAll(orderPriceChanges(detailDTO, currency));
		}
		
		return result;
	}
	
	private Map<String,String> orderPriceChanges(OrderDetailDTO detailDTO, Currency currency){
		Map<String, String> result = new HashMap<String, String>();
		NumberFormat numberFormat = new DecimalFormat("###,###,###,###,##0.00");
		result.put("normalDeliverPrice", numberFormat.format(orderService.getDeliverPrice(detailDTO.getShippingCode())*currency.getExchangeRateBaseOnDefault()));
		result.put("expeditedDeliverPrice", numberFormat.format(orderService.getExpeditedDeliverPrice(detailDTO.getShippingCode())*currency.getExchangeRateBaseOnDefault()));
		result.put("orderProductTotal", numberFormat.format(detailDTO.getTotalProductPrice()*currency.getExchangeRateBaseOnDefault()));
		result.put("couponPrice", numberFormat.format(detailDTO.getCouponCutOff()*currency.getExchangeRateBaseOnDefault()));
		result.put("shippingCost", numberFormat.format(detailDTO.getDeliveryPrice()*currency.getExchangeRateBaseOnDefault()));
		
		return result;
	}
	
	@Secured("USER")
	@RequestMapping("/sp/payment/updateShippingMethod")
	public ResponseResult<OrderDetailDTO> updateShippingMethod(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 @RequestParam("shippingMethod")String shippingMethod
			 ){
		
		OrderDetailDTO detailDTO = orderService.getOrderDetailById(orderId);
		
		ResponseResult<OrderDetailDTO> result = null;
		
		if(null == detailDTO.getShippingCode()){
			result = new ResponseResult<OrderDetailDTO>(false, detailDTO);
			result.getMessageMap().put("ERROR", "Please fill shipping address");
		}else{
			detailDTO = orderService.updateShippingMethod(orderId, shippingMethod);
			result = new ResponseResult<OrderDetailDTO>(true, detailDTO);
			result.getMessageMap().putAll(orderPriceChanges(detailDTO, currency));
		}
		
		return result;
	}
	
	@Secured("USER")
	@RequestMapping("/sp/payment/applyCoupon")
	public ResponseResult<OrderDetailDTO> applyCoupon(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 @RequestParam("addressId")long addressId,
			 @RequestParam("couponCode")String couponCode
			 ){
		
		OrderDetailDTO detailDTO = orderService.getOrderDetailById(orderId);
		
		Coupon coupon = couponService.getCouponByCode(couponCode);
		
		ResponseResult<OrderDetailDTO> result = null;
		
		String error = null;
		
		if(null == coupon){
			error = "Invalid Coupon";
		}
		
		if(coupon.getEndDate().before(new Date()) || coupon.getUsedCount() >= coupon.getMaxUsedCount()){
			error = "Coupon Expired";
		}
		
		if(coupon.getMinCost() > detailDTO.getTotalProductPrice()){
			error = "Cannot apply to this order";
		}
		
		
		if(null != error){
			result = new ResponseResult<OrderDetailDTO>(false, detailDTO);
			result.getMessageMap().put("ERROR", error);
		}else{
			result = new ResponseResult<OrderDetailDTO>(true, detailDTO);
			result.getMessageMap().putAll(orderPriceChanges(detailDTO, currency));
		}
		
		
		return result;
	}
	
	@Secured("USER")
	@RequestMapping("/sp/payment/applyCustomerMsg")
	public ResponseResult<OrderDetailDTO> applyCustomerMsg(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 @RequestParam("message")String message
			 ){
		
		OrderDetailDTO detailDTO = orderService.applMessage(orderId, message);
		
		
		ResponseResult<OrderDetailDTO> result = new ResponseResult<OrderDetailDTO>(true, detailDTO);
		
		return result;
	}
	
	
	@RequestMapping("/sp/payment/checkout/{orderId}")
	public String cartToCheckout(@PathVariable("orderId") long orderId, Model model){
		return "redirect:/sp/shoppingcart/payment/"+orderId;
	}
	
	@RequestMapping("/sp/payment/payment/{orderId}")
	public String cartToPay(@PathVariable("orderId") long orderId, Model model){
		return "shoppingcatPayment";
	}

	public CouponService getCouponService() {
		return couponService;
	}

	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}
	
}
