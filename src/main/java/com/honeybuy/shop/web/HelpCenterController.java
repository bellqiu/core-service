package com.honeybuy.shop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.HtmlServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;
import com.honeybuy.shop.web.dto.PageMeta;

@Controller
@RequestMapping("/help")
public class HelpCenterController {
	
	private static final Logger logger = LoggerFactory.getLogger(HelpCenterController.class);
	
	@Autowired
	HtmlServiceCacheWrapper htmlService;
	
	@Autowired
	SettingServiceCacheWrapper settingService;
	
	@RequestMapping("/about-us")
	public String aboutUs(Model model) {
		logger.debug("Go to about Us page");
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_ABOUT_US, "About Us");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "About Us");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_ABOUT_US, "About Us");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_ABOUT_US, "About Us");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_ABOUT_US, "About Us");
		addPageMeta(model, title, keyword, description);
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
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/customer-service")
	public String customerService(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_CUSTOMER_SERVICE, "Customer Service");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Customer Service");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_CUSTOMER_SERVICE, "Customer Service");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_CUSTOMER_SERVICE, "Customer Service");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_CUSTOMER_SERVICE, "Customer Service");
		addPageMeta(model, title, keyword, description);
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
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/legal-window")
	public String legalWindow(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LEGAL_WINDOW, "Legal Window");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Legal Window");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_LEGAL_WINDOW, "Legal Window");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_LEGAL_WINDOW, "Legal Window");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_LEGAL_WINDOW, "Legal Window");
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/warranty-return")
	public String warrantyReturn(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_WARRANTY_AND_RETURN, "Warranty and Return");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Warranty and Return");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_WARRANTY_AND_RETURN, "Warranty and Return");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_WARRANTY_AND_RETURN, "Warranty and Return");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_WARRANTY_AND_RETURN, "Warranty and Return");
		addPageMeta(model, title, keyword, description);
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
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/terms-conditions")
	public String termsConditions(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_TERMS_AND_CONDITIONS, "Terms and Conditions");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Terms and Conditions");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_TERMS_AND_CONDITIONS, "Terms and Conditions");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_TERMS_AND_CONDITIONS, "Terms and Conditions");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_TERMS_AND_CONDITIONS, "Terms and Conditions");
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/shipping-handling")
	public String shippingHandling(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_SHIPPING_HANDLING, "Shipping&Handling");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Shipping&Handling");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_SHIPPING_HANDLING, "Shipping and Handling");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_SHIPPING_HANDLING, "Shipping and Handling");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_SHIPPING_HANDLING, "Shipping and Handling");
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/testimonials")
	public String testimonials(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_TESTIMONIALS, "Testimonials");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Testimonials");
		model.addAttribute("page", "customerService");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_TESTIMONIALS, "Testimonials");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_TESTIMONIALS, "Testimonials");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_TESTIMONIALS, "Testimonials");
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/faq")
	public String faq(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_FAQ, "FAQ");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "FAQ");
		model.addAttribute("page", "myAccount");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_FAQ, "FAQ");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_FAQ, "FAQ");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_FAQ, "FAQ");
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	@RequestMapping("/shipping-and-handling")
	public String shippingAndHandling(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_SHIPPING_HANDLING, "Shipping & Handling");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Shipping & Handling");
		model.addAttribute("page", "otherInfomation");
		
		String title = settingService.getStringValue(Constants.SETTING_TITLE_SHIPPING_HANDLING, "Shipping and Handling");
		String keyword = settingService.getStringValue(Constants.SETTING_KEYWORD_SHIPPING_HANDLING, "Shipping and Handling");
		String description = settingService.getStringValue(Constants.SETTING_DESCRIPTION_SHIPPING_HANDLING, "Shipping and Handling");
		addPageMeta(model, title, keyword, description);
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
		addPageMeta(model, title, keyword, description);
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
		addPageMeta(model, title, keyword, description);
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
		addPageMeta(model, title, keyword, description);
		return "helpCenter";
	}
	
	
	public void addPageMeta(Model model, String title, String keywords, String description) {
		PageMeta meta = new PageMeta();
		meta.setTitle(title);
		meta.setKeywords(keywords);
		meta.setDescription(description);
		model.addAttribute("pageMeta", meta);
	}

}
