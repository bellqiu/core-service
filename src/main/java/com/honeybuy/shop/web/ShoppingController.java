/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Address;
import com.hb.core.entity.Coupon;
import com.hb.core.entity.Currency;
import com.hb.core.entity.Order;
import com.hb.core.service.CouponService;
import com.hb.core.service.EmailService;
import com.hb.core.service.OrderService;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.dto.ResponseResult;
import com.honeybuy.shop.web.eds.SiteDirectService;
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
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private SettingServiceCacheWrapper settingService;
	
	@Autowired
	private SiteDirectService siteService;
	
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
				orderDetailDTO = orderService.updateOrderShippingAddress(details.getUsername(), addresses.get(0), orderId);
			}
			
			if(billingAddIdRef > 0){
				billingAdd = userService.getUserAddressById(billingAddIdRef, useremail);
			}
			
			if(null == billingAdd){
				orderDetailDTO = orderService.updateOrderBillingAddress(details.getUsername(), addresses.get(0), orderId);
			}
			
			model.addAttribute("addresses", addresses);
		}
		
		
		if(null != orderDetailDTO && useremail.equals(orderDetailDTO.getUseremail())){
			model.addAttribute("currentOrder", orderDetailDTO);
		}

		TreeMap<String, String> paymentMethodList = new TreeMap<String, String>();
		
		String methodStrs = settingService.getStringValue(Constants.PAYMENT_METHOD_LIST, "");
		
		if(!StringUtils.isEmpty(methodStrs)){
			String[] payments = methodStrs.split(",");
			for (String payment : payments) {
				String paymentKey = payment.split("=")[0].trim();
				String paymentTitle = paymentKey;
				if( payment.split("=").length > 1){
					paymentTitle = payment.split("=")[1].trim();
				}
				
				paymentMethodList.put(paymentKey, paymentTitle);
			}
		}
		model.addAttribute("defaultPayment", paymentMethodList.firstKey());
		model.addAttribute("paymentMethods", paymentMethodList);
		model.addAttribute("normalDeliverPrice", orderService.getDeliverPrice(orderDetailDTO.getShippingCode(), orderDetailDTO.getItemTotal()));
		model.addAttribute("expeditedDeliverPrice", orderService.getExpeditedDeliverPrice(orderDetailDTO.getShippingCode(), orderDetailDTO.getItemTotal()));
		
		return "shoppingcatAddress";
	}
	
	@RequestMapping("/sp/payment/updateOrderAdd")
	@ResponseBody
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
			detailDTO = orderService.updateOrderShippingAddress(details.getUsername(), address, orderId);
		}else{
			detailDTO = orderService.updateOrderBillingAddress(details.getUsername(), address, orderId);
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
		result.put("normalDeliverPrice", numberFormat.format(orderService.getDeliverPrice(detailDTO.getShippingCode(), detailDTO.getItemTotal())*currency.getExchangeRateBaseOnDefault()));
		result.put("expeditedDeliverPrice", numberFormat.format(orderService.getExpeditedDeliverPrice(detailDTO.getShippingCode(),  detailDTO.getItemTotal())*currency.getExchangeRateBaseOnDefault()));
		result.put("orderProductTotal", numberFormat.format(detailDTO.getTotalProductPrice()*currency.getExchangeRateBaseOnDefault()));
		result.put("couponPrice", numberFormat.format(detailDTO.getCouponCutOff()*currency.getExchangeRateBaseOnDefault()));
		result.put("shippingCost", numberFormat.format(detailDTO.getDeliveryPrice()*currency.getExchangeRateBaseOnDefault()));
		result.put("grandTotal", numberFormat.format(detailDTO.getGrandTotal()*currency.getExchangeRateBaseOnDefault()));
		
		return result;
	}
	
	@ResponseBody
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
			detailDTO = orderService.updateShippingMethod(details.getUsername(), orderId, shippingMethod);
			result = new ResponseResult<OrderDetailDTO>(true, detailDTO);
			result.getMessageMap().putAll(orderPriceChanges(detailDTO, currency));
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/sp/payment/applyCoupon")
	public ResponseResult<OrderDetailDTO> applyCoupon(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 @RequestParam("couponCode")String couponCode
			 ){
		
		OrderDetailDTO detailDTO = orderService.getOrderDetailById(orderId);
		
		Coupon coupon = couponService.getCouponByCode(couponCode);
		
		ResponseResult<OrderDetailDTO> result = null;
		
		String error = null;
		
		if(null == coupon){
			error = "Invalid Coupon";
		}else{
			if(coupon.getEndDate().before(new Date()) || coupon.getUsedCount() >= coupon.getMaxUsedCount()){
				error = "Coupon Expired";
			}
			
			if(coupon.getMinCost() > detailDTO.getTotalProductPrice()){
				error = "Cannot apply to this order";
			}
		}
		
		if(null != error){
			result = new ResponseResult<OrderDetailDTO>(false, detailDTO);
			result.getMessageMap().put("ERROR", error);
		}else{
			detailDTO = orderService.applyCoupon(details.getUsername(), orderId, coupon.getCode());
			result = new ResponseResult<OrderDetailDTO>(true, detailDTO);
			result.getMessageMap().putAll(orderPriceChanges(detailDTO, currency));
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/sp/payment/checkOrderPaymentInfo", method={RequestMethod.POST})
	@ResponseBody
	public ResponseResult<Boolean> checkOrderPaymentInfo(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 @RequestParam("message")String message
			 ){
		
		OrderDetailDTO detailDTO = orderService.applMessage(details.getUsername(), orderId, message);
		
		ResponseResult<Boolean> result = new ResponseResult<Boolean>(true, true);
		
		if(null != detailDTO){
			if(StringUtils.isEmpty(detailDTO.getShippingAddress()) || detailDTO.getShippingAddressRef() < 1){
				result.setResult(false);
				result.setSuccess(false);
				result.getMessageMap().put("ERROR", "Please fill the Shipping Address");
			}else if(StringUtils.isEmpty(detailDTO.getShippingMethod())){
				result.setResult(false);
				result.setSuccess(false);
				result.getMessageMap().put("ERROR", "Please select a Shipping Method");
			}else if(StringUtils.isEmpty(detailDTO.getBillingAddress()) || detailDTO.getBillingAddressRef() < 1){
				result.setResult(false);
				result.setSuccess(false);
				result.getMessageMap().put("ERROR", "Please fill the Billing Address");
			}
		}else{
			result.setResult(false);
			result.setSuccess(false);
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/sp/payment/checkout", method={RequestMethod.POST})
	@ResponseBody
	public ResponseResult<Boolean> checkoutOrder2Pending(
			@RequestParam(value="orderId") long orderId, 
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency
			 ){
		
		final OrderDetailDTO orderDetailDTO = orderService.updateOrderInfo(orderId, "", Order.Status.PENDING, currency.getCode());
		
		//TODO pending email
		new Thread(){
            public void run() {
                try{
					emailService.sendPayOrderMail(orderDetailDTO);
                } catch (Exception e){
                }
            };
        }.start();
		
		return  new ResponseResult<Boolean>(true, true);
	}
	
	
	@RequestMapping("/sp/payment/{payment}/checkout/{orderId}")
	public String paypalCheckout(@PathVariable("orderId") long orderId, Model model, @PathVariable("payment") String payment,
			 @SessionAttribute(value=Constants.LOGINUSER_SESSION_ATTR)UserDetails details,
			 @SessionAttribute("defaultCurrency")Currency currency,
			 HttpServletRequest request){
		
		OrderDetailDTO detailDTO = orderService.getOrderDetailById(orderId);
		
		if(null == detailDTO){
			return "404";
		}
		
		ResponseResult<Boolean> result = checkOrderPaymentInfo(orderId, details, currency, detailDTO.getCustomerMsg());
		
		if(result.isSuccess()){
			
			model.addAttribute("currentOrder", detailDTO);
			
			if("paypal".equals(payment)){
				
				String serverHost = siteService.getSite().getDomain() ;
				
				model.addAttribute("notifyUrl", serverHost + settingService.getStringValue(Constants.PAYPAL_NOTIFY_URI, Constants.PAYPAL_NOTIFY_URI_DEFAULT));
				
				model.addAttribute("paypalAccount", settingService.getStringValue(Constants.PAYPAL_ACCOUNT, Constants.PAYPAL_ACCOUNT_DEFAULT));
				
				model.addAttribute("returnUrl", serverHost + "/od/orderDetail?orderId=" + orderId);
				
				
			}
			
			return payment;
		}
		
		return "redirect:/sp/payment/paymentInfo/?orderId="+orderId;
	}
	
	
	@RequestMapping(value="/sp/notify/paypal")
	public String paypalNotify(HttpServletRequest request) throws IOException{

		List<String> errorStrings = new ArrayList<String>();
		List<String> msgs = new ArrayList<String>();
		
		Enumeration en = request.getParameterNames();
		String str = "cmd=_notify-validate";
		logger.info("################Accept######################");
		while (en.hasMoreElements()) {
			String paramName = (String) en.nextElement();
			String paramValue = request.getParameter(paramName);
			str = str + "&" + paramName + "="
					+ URLEncoder.encode(paramValue, "iso-8859-1");
			logger.info(paramName+": " + request.getParameter(paramName));
		}
		logger.info("######################################");
		logger.info("str: " + str);
		logger.info("######################################");
//		URL u = new URL("http://www.sandbox.paypal.com/c2/cgi-bin/webscr");
		 URL u = new URL("http://www.paypal.com/cgi-bin/webscr");
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);

		uc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(str);
		pw.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				uc.getInputStream()));
		String res = in.readLine();
		in.close();
		
		
		String itemName = request.getParameter("item_name");
		String quantity = request.getParameter("quantity");
		String paymentStatus = request.getParameter("payment_status");
		String paymentAmount = request.getParameter("mc_gross");
		String paymentCurrency = request.getParameter("mc_currency");
		String txnId = request.getParameter("txn_id");
		String receiverEmail = request.getParameter("receiver_email");
		
		String payerEmail = request.getParameter("payer_email");
		String address_city = request.getParameter("address_city");
		String contact_phone = request.getParameter("contact_phone");
		String address_country = request.getParameter("address_country");
		String address_street = request.getParameter("address_street");
		String address_zip = request.getParameter("address_zip");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		Enumeration els = request.getParameterNames();
		
		if ("VERIFIED".equals(res)) {
			OrderDetailDTO order = orderService.getOrderBySN(itemName);
			logger.info(">>>>>>>>>>>>>>>>>>>VERIFIED>>>>>>>>>>>>>>>>>>>>>>");
			if(null!=order){
				logger.info(">>>>>>>>>>>>>>>>>>>paymentAmount:"+paymentAmount+">>>>>>>>>>>>>>>>>>>>>>");
				logger.info(">>>>>>>>>>>>>>>>>>>paymentCurrency:"+paymentCurrency+">>>>>>>>>>>>>>>>>>>>>>");
				logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail:"+receiverEmail+">>>>>>>>>>>>>>>>>>>>>>");
				logger.info(">>>>>>>>>>>>>>>>>>>itemNumber:"+quantity+">>>>>>>>>>>>>>>>>>>>>>");
				
				logger.info(">>>>>>>>>>>>>>>>>>>itemNumber.equals('1'):"+quantity.equals("1")+">>>>>>>>>>>>>>>>>>>>>>");
				logger.info(">>>>>>>>>>>>>>>>>>>order.getCurrency().equals(paymentCurrency):"+order.getCurrency().equals(paymentCurrency)+">>>>>>>>>>>>>>>>>>>>>>");
				
				siteService.getAllCurrency();
				
				float rate = 1;
				
				List<Currency> currencies = siteService.getAllCurrency();
				
				for (Currency currency : currencies) {
					if(currency.getCode().equals(paymentCurrency)){
						rate = currency.getExchangeRateBaseOnDefault();
					}
						
				}
				
				float amount = rate * order.getGrandTotal();
				
				if((amount - 0.01) <= Float.parseFloat(paymentAmount)
						&&order.getCurrency().equals(paymentCurrency)
						&&receiverEmail.equalsIgnoreCase(settingService.getStringValue(Constants.PAYPAL_ACCOUNT, Constants.PAYPAL_ACCOUNT_DEFAULT))
						&&quantity.equals("1")){
					try{
						final OrderDetailDTO orderDetailDTO = orderService.updateOrderInfo(order.getId(), "", Order.Status.PAID, paymentCurrency);
						
						//TODO ORDER payment finished
						new Thread(){
				            public void run() {
				                try{
									emailService.sendReceiveOrderPaymentMail(orderDetailDTO);
				                } catch (Exception e){
				                }
				            };
				        }.start();
						
					}catch(Exception e){
						logger.info(e.getMessage(),e);
					}
				}else{
					logger.info(">>>>>>>>>>>>>>>>>>>NOT enough mony>>>>>>>>>>>>>>>>>>>>>>");
				}
				
			}
			
			
		} else if ("INVALID".equals(res)) {
			logger.info("##############INVALID########################");
		} else {
			logger.info("##############ORTHER########################");
		}

		
		return null;
	}
	

	
	public CouponService getCouponService() {
		return couponService;
	}

	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}

	public SettingServiceCacheWrapper getSettingService() {
		return settingService;
	}

	public void setSettingService(SettingServiceCacheWrapper settingService) {
		this.settingService = settingService;
	}

	public ProductServiceCacheWrapper getProductService() {
		return productService;
	}

	public void setProductService(ProductServiceCacheWrapper productService) {
		this.productService = productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
