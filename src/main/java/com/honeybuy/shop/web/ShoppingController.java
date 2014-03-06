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
import javax.servlet.http.HttpServletResponse;

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
import com.hb.core.exception.CoreServiceException;
import com.hb.core.service.CouponService;
import com.hb.core.service.EmailService;
import com.hb.core.service.HBNVPCallerService;
import com.hb.core.service.OrderService;
import com.hb.core.service.UserService;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.shared.dto.OrderItemDTO;
import com.hb.core.shared.dto.UserDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.dto.ResponseResult;
import com.honeybuy.shop.web.eds.SiteDirectService;
import com.honeybuy.shop.web.interceptor.SessionAttribute;
import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.core.nvp.NVPEncoder;
import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.util.Util;

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
	
	@Autowired
	private HBNVPCallerService hbNVPCallerService;
	
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
			OrderDetailDTO order = orderService.getOrderBySN(itemName.replace("Order SN:", "").trim());
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
	
	@RequestMapping(value="/sp/directpay/paypal")
	public String paypalDirectPay(Model model, @CookieValue(defaultValue="", required=false, value=Constants.TRACKING_COOKE_ID_NAME)String trackingId,
									@RequestParam(defaultValue="0", value="orderId")long orderId,
									HttpServletResponse response,
									HttpServletRequest request, @SessionAttribute("defaultCurrency")Currency currency) throws PayPalException, IOException{
		
		OrderDetailDTO detailDTO = null;
		
		if(orderId > 0 ){
			detailDTO = orderService.getOrderDetailById(orderId);
		}else{
			detailDTO = orderService.getCart(trackingId, null);
		}
		
		HBNVPCallerService caller = hbNVPCallerService;
	    
		caller.login();
		
		StringBuffer url = new StringBuffer();
//		url.append("http://");
		url.append(siteService.getSite().getDomain()+"/sp/directpay/paypal/return?orderId="+detailDTO.getId());
		String returnURL = url.toString() /*+ "/nvp/GetExpressCheckoutDetails.jsp?=" + "&currencyCodeType=" + request.getParameter("currencyCodeType")*/;
		String cancelURL = url.toString() /* + "/nvp/SetExpressCheckout.jsp?"+"paymentType=" + request.getParameter("paymentType")*/ ;
		
		String strNVPRequest = "";
		StringBuffer sbErrorMessages= new StringBuffer("");

		//NVPEncoder object is created and all the name value pairs are loaded into it.
		NVPEncoder encoder = new NVPEncoder();

		
		encoder.add("CANCELURL",cancelURL);
		encoder.add("RETURNURL",returnURL);
		
		/*encoder.add("PAYMENTREQUEST_0_TAXAMT","2.00");*/
		encoder.add("METHOD","SetExpressCheckout");
		encoder.add("PAYMENTREQUEST_0_CURRENCYCODE", currency.getCode());
		/*encoder.add("PAYMENTREQUEST_0_AMT","10");*/
		NumberFormat numberFormat = new DecimalFormat("#,###,##0.00");
		float totalAmount = 0;
		for (int i = 0; i < detailDTO.getItems().size(); i++) {
			OrderItemDTO itemDTO = detailDTO.getItems().get(i);
			encoder.add("L_PAYMENTREQUEST_0_DESC"+i, itemDTO.getProductSummary().getTitle());
			encoder.add("L_PAYMENTREQUEST_0_QTY"+i, itemDTO.getQuantity()+"");
			float itemAmoumt = itemDTO.getFinalPrice() *  currency.getExchangeRateBaseOnDefault();
			encoder.add("L_PAYMENTREQUEST_0_AMT"+i, numberFormat.format(itemAmoumt));
			totalAmount+= itemDTO.getQuantity() * itemAmoumt;
		}
		
		
	/*	encoder.add("L_SHIPPINGOPTIONlABEL1","UPS Next Day Air");
		encoder.add("L_SHIPPINGOPTIONLABEL0","UPS Ground 7 Days");
		encoder.add("L_SHIPPINGOPTIONNAME0","Ground");
		encoder.add("L_SHIPPINGOPTIONNAME1","UPS Air");
		encoder.add("L_SHIPPINGOPTIONISDEFAULT1","true");
		encoder.add("L_SHIPPINGOPTIONISDEFAULT0","false");
		encoder.add("L_SHIPPINGOPTIONAMOUNT1","8");
		encoder.add("L_SHIPPINGOPTIONAMOUNT0","3");*/
		
		encoder.add("SHIPPINGOPTIONAMOUNT","0");
		
		encoder.add("PAYMENTREQUEST_0_ITEMAMT", numberFormat.format(totalAmount));
		encoder.add("PAYMENTREQUEST_0_SHIPPINGAMT","0");
		
		encoder.add("PAYMENTREQUEST_0_AMT",numberFormat.format(totalAmount));
		
		
		
		
		//encode method will encode the name and value and form NVP string for the request	
		strNVPRequest = encoder.encode(); 

		//call method will send the request to the server and return the response NVPString
		String ppresponse =
			(String) caller.call( strNVPRequest);

		//NVPDecoder object is created
		NVPDecoder resultValues = new NVPDecoder();
		
		//decode method of NVPDecoder will parse the request and decode the name and value pair			
		resultValues.decode(ppresponse);
		
			//checks for Acknowledgement and redirects accordingly to display error messages		
		String strAck = resultValues.get("ACK"); 
		if(strAck !=null && !(strAck.equals("Success") || strAck.equals("SuccessWithWarning")))
		{
			return null;
		}else {
				
			response.sendRedirect("https://www."+"sandbox"+".paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+resultValues.get("TOKEN"));
					
		}
		
		/*return "redirect:/sp/directpay/paypal/return?EMAIL=test@test.com";*/
		//return "directpay_paypal";
		return null;
	}
	
	@RequestMapping("/sp/directpay/paypal/rs")
	public String paypalDirectPayRS(Model model){
		return "directpay_paypal_rs";
	}
	
	
	@RequestMapping(value="/sp/directpay/paypal/return")
	public String paypalDirectPayDetails(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value="orderId")long orderId) throws PayPalException, IOException{
		hbNVPCallerService.login();
		NVPEncoder detailRerequest = new NVPEncoder();
		detailRerequest.add("METHOD","GetExpressCheckoutDetails");
		detailRerequest.add("TOKEN",request.getParameter("token"));
		
		String detailRerequestResponse =
				(String) hbNVPCallerService.call( detailRerequest.encode());
		
		NVPDecoder detailRerequesttValues = new NVPDecoder();
		detailRerequesttValues.decode(detailRerequestResponse);
		
		NVPDecoder resultValues=detailRerequesttValues;
		
//		System.out.println("EMAIL:"+resultValues.get("EMAIL"));
		
//		System.out.println("EMAIL:"+request.getParameter("EMAIL"));
		
		//response.getWriter().print("EMAIL:"+request.getParameter("EMAIL"));
		
		String username = resultValues.get("EMAIL");
		
		Map<String, String> map = resultValues.getMap();
		System.out.println("############################################################");
		for (Map.Entry<String, String> en : map.entrySet()) {
			System.out.println(en.getKey()+"="+en.getValue());
		}
		System.out.println("############################################################");
		
        NVPEncoder encoder = new NVPEncoder();
		encoder.add("METHOD","DoExpressCheckoutPayment");
		encoder.add("TOKEN",resultValues.get("TOKEN"));
		encoder.add("PAYERID",resultValues.get("PAYERID"));
		encoder.add("PAYMENTREQUEST_0_AMT", resultValues.get("PAYMENTREQUEST_0_AMT"));
		encoder.add("PAYMENTREQUEST_0_CURRENCYCODE",resultValues.get("PAYMENTREQUEST_0_CURRENCYCODE"));
	
		//encode method will encode the name and value and form NVP string for the request		
	    String strNVPString = encoder.encode();
	
		//call method will send the request to the server and return the response NVPString    	
		String strNVPResponse =
			(String) hbNVPCallerService.call( strNVPString);
			
		//NVPDecoder object is created			
		NVPDecoder decoder = new NVPDecoder();
		
		//decode method of NVPDecoder will parse the request and decode the name and value pair		
		decoder.decode(strNVPResponse);
		
		OrderDetailDTO detailDTO = null;
	    
		//checks for Acknowledgement and redirects accordingly to display error messages		
		String strAck = decoder.get("ACK"); 
		if(strAck !=null && !(strAck.equals("Success") || strAck.equals("SuccessWithWarning")))
		{
			model.addAttribute("status", "Failed");
			return "directpay_paypal_rs";
		}else{
			
			UserDTO user = null;
			try {
				if (!StringUtils.isEmpty(username)) {
					user = userService.newThirdPartyUserIfNotExisting(username, "paypal");
					
					detailDTO = orderService.getOrderDetailById(orderId);
					
					orderService.directCheckout(detailDTO, new Address(), user);
					
					final UserDTO usr = user;
					if(user != null) {
						new Thread(){
			                public void run() {
			                    try{
									emailService.sendRegisterMail(usr.getEmail(), usr.getPassword());
			                    } catch (Exception e){
			                    }
			                };
			            }.start();
					}
				}
				
				
			} catch(CoreServiceException e) {
				model.addAttribute("isSignUpPage", true);
				model.addAttribute("isSignUpFail", true);
				return "forward:/ac/login";
			}
			
			model.addAttribute("username", user.getEmail());
			model.addAttribute("password", user.getPassword());
			model.addAttribute("targetUrl", siteService.getSite().getDomain()+"/sp/directpay/paypal/rs?orderId="+detailDTO.getId());
			
			return "loging";
		
			
		}
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

	public HBNVPCallerService getHbNVPCallerService() {
		return hbNVPCallerService;
	}

	public void setHbNVPCallerService(HBNVPCallerService hbNVPCallerService) {
		this.hbNVPCallerService = hbNVPCallerService;
	}
	
}
