package com.honeybuy.shop.web;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.service.EmailService;
import com.hb.core.service.SubscribeService;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.PageMetaUtils;
import com.honeybuy.shop.web.cache.HtmlServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.dto.MailEntity;
import com.honeybuy.shop.web.dto.ResponseResult;
import com.honeybuy.shop.web.dto.SupportEntity;

@Controller
@RequestMapping("/help")
public class HelpCenterController {
	
	private static final Logger logger = LoggerFactory.getLogger(HelpCenterController.class);
	
	@Autowired
	HtmlServiceCacheWrapper htmlService;
	
	@Autowired
	SettingServiceCacheWrapper settingService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	SubscribeService subscribeService;
	
	@RequestMapping("/about-us")
	public String aboutUs(Model model) {
		logger.debug("Go to about Us page");
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_ABOUT_US, "About Us");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "About Us");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_ABOUT_US, "About Us");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_ABOUT_US, "About Us");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_ABOUT_US, "About Us");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/contact-us")
	public String contactUs(Model model) {
		logger.debug("Go to contact Us page");
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_CONTACT_US, "Contact Us");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Contact Us");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_CONTACT_US, "Contact Us");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_CONTACT_US, "Contact Us");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_CONTACT_US, "Contact Us");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/privacy-policies")
	public String privacyPolicies(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_PRIVACY_POLICIES, "Privacy Policies");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Privacy Policies");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_PRIVACY_POLICIES, "Privacy Policies");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_PRIVACY_POLICIES, "Privacy Policies");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_PRIVACY_POLICIES, "Privacy Policies");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/copyright")
	public String copyright(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_COPYRIGHT, "Copyright");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Copyright");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_COPYRIGHT, "Copyright");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_COPYRIGHT, "Copyright");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_COPYRIGHT, "Copyright");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/return-policy")
	public String returnPolicy(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_RETURN_POLICY, "Return Policy");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Return Policy");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_RETURN_POLICY, "Return Policy");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_RETURN_POLICY, "Return Policy");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_RETURN_POLICY, "Return Policy");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/payment-methods")
	public String paymentMethods(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_PAYMENT_METHODS, "Payment Methods");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Payment Methods");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_PAYMENT_METHODS, "Payment Methods");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_PAYMENT_METHODS, "Payment Methods");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_PAYMENT_METHODS, "Payment Methods");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/terms-use")
	public String termsOfUse(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_TERMS_OF_USE, "Terms of Use");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Terms of Use");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_TERMS_OF_USE, "Terms of Use");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_TERMS_OF_USE, "Terms of Use");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_TERMS_OF_USE, "Terms of Use");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/shipping-handling")
	public String shippingHandling(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_SHIPPING_HANDLING, "Shipping&Handling");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Shipping & Handling");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_SHIPPING_HANDLING, "Shipping and Handling");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_SHIPPING_HANDLING, "Shipping and Handling");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_SHIPPING_HANDLING, "Shipping and Handling");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/wholesale")
	public String wholesale(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_WHOLESALE, "Wholesale");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Wholesale");
		model.addAttribute("page", "myAccount");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_WHOLESALE, "Wholesale");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_WHOLESALE, "Wholesale");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_WHOLESALE, "Wholesale");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	/*@RequestMapping("/faq")
	public String faq(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_FAQ, "FAQ");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "FAQ");
		model.addAttribute("page", "myAccount");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_FAQ, "FAQ");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_FAQ, "FAQ");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_FAQ, "FAQ");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/large-order-quotations")
	public String largeOrderQuotations(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LARGE_ORDER_QUOTATIONS, "Large Order Quotations");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Large Order Quotations");
		model.addAttribute("page", "otherInfomation");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_LARGE_ORDER_QUOTATIONS, "Large Order Quotations");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_LARGE_ORDER_QUOTATIONS, "Large Order Quotations");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_LARGE_ORDER_QUOTATIONS, "Large Order Quotations");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/lowest-price-guarantee")
	public String lowestPriceGuarantee(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LOWEST_PRICE_GUARANTEE, "Lowest Price Guarantee");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Lowest Price Guarantee");
		model.addAttribute("page", "otherInfomation");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_LOWEST_PRICE_GUARANTEE, "Lowest Price Guarantee");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_LOWEST_PRICE_GUARANTEE, "Lowest Price Guarantee");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_LOWEST_PRICE_GUARANTEE, "Lowest Price Guarantee");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/look-for-vendors")
	public String lookForVendors(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LOOK_FOR_VENDORS, "Look for Vendors");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Look for Vendors");
		model.addAttribute("page", "otherInfomation");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_LOOK_FOR_VENDORS, "Look for Vendors");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_LOOK_FOR_VENDORS, "Look for Vendors");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_LOOK_FOR_VENDORS, "Look for Vendors");
		PageMetaUtils.addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}*/
	
	@RequestMapping(value="/support", method=RequestMethod.GET)
	public String support(Model model) {
		SupportEntity supportEntity = new SupportEntity("", "", "", "", "");
		model.addAttribute("supportEntity", supportEntity);
		return "support";
	}
	
	@RequestMapping(value="/support", method=RequestMethod.POST)
	public String supportPost(
			@RequestParam("subject") String subject, 
			@RequestParam("email")String email,
			@RequestParam("message")String message,
			@RequestParam(value="order_number", required=false, defaultValue="") String orderNumber,
			@RequestParam(value="phone_number", required=false, defaultValue="") String phoneNumber,
			Model model) {
		//logger.debug("subject: {}, email: {}, message: {}, order number: {}, phone number: {}", new Object[]{subject, email, message, orderNumber, phoneNumber});
		SupportEntity supportEntity = new SupportEntity(email, subject, message);
		if(orderNumber != null) {
			supportEntity.setOrderNumber(orderNumber);
		} else {
			supportEntity.setOrderNumber("");
		}
		if(phoneNumber != null) {
			supportEntity.setPhoneNumber(phoneNumber);
		} else {
			supportEntity.setPhoneNumber("");
		}
		if(!EmailService.validateEmail(email)) {
			model.addAttribute("supportEntity", supportEntity);
			model.addAttribute("emailError", true);
			return "support";
		}
		emailService.sendSubmitSupport(email, supportEntity);
		return "supportSubmited";
	}
	
	@RequestMapping(value="/subscribe", method=RequestMethod.GET)
	@ResponseBody
	public ResponseResult<String> subscribe(
			@RequestParam("email")String email,
			Model model) {
		boolean result = subscribeService.saveSubscriber(email);
		if(result) {
			return new ResponseResult<String>(true, "Success to subscribe email " + email);
		} else {
			return new ResponseResult<String>(false, "Subscribe fail. Email format is not correct.");
		}
	}
	
	@RequestMapping(value="/unsubscribe", method=RequestMethod.GET)
	public String unsubscribe(
			@RequestParam(value="email", required=false)String email,
			Model model) {
		if(email == null) {
			model.addAttribute("noEmail", true);
		} else if(!EmailService.validateEmail(email)) {
			model.addAttribute("invalidEmail", true);
		}
		model.addAttribute("email", email);
		return "unsubscribe";
	}
	
	@RequestMapping(value="/unsubscribe", method=RequestMethod.POST)
	public String unsubscribePost(
			@RequestParam("email")String email,
			Model model) {
		if(!EmailService.validateEmail(email)) {
			model.addAttribute("invalidEmail", true);
		} else {
			logger.debug("Processing to unsubscribe for [{}]", email);
			// TODO unsubscribe
			subscribeService.disableSubscriber(email);
			model.addAttribute("requestSuccess", true);
		}
		
		return "unsubscribe";
	}
	
	@RequestMapping(value="/changeSubscribe", method=RequestMethod.POST)
	public String changeSubscribe(
			@RequestParam("email")String email,
			@RequestParam("newEmail")String newEmail,
			Model model) {
		if(!EmailService.validateEmail(newEmail)) {
			model.addAttribute("invalidEmail", true);
		} else {
			logger.debug("Processing to change subscriber from [{}] to [{}]", new Object[]{email, newEmail});
			if(!newEmail.equals(email)) {
				subscribeService.changeSubscriber(email, newEmail);
			}
			model.addAttribute("requestSuccess", true);
		}
		return "unsubscribe";
	}
	
	@RequestMapping(value="/changeSubscribe", method=RequestMethod.GET)
	public String changeSubscribe(
			Model model) {
		return "redirect:/help/unsubscribe";
	}
	
	@RequestMapping(value = "/sendmail", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseResult<String> sendMail(@RequestBody MailEntity mailEntity) {
		ResponseResult<String> response = new ResponseResult<String>();
		if(mailEntity == null) {
			response.setSuccess(false);
			response.setResult("MailEntity is null");
			return response;
		} else {
			emailService.sendProxyMail(mailEntity, response);
		}
		return response;
	}
}
