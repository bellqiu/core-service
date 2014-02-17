package com.honeybuy.shop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hb.core.entity.HTML;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.HtmlServiceCacheWrapper;
import com.honeybuy.shop.web.dto.PageMeta;

@Controller
@RequestMapping("/help")
public class HelpCenterController {
	
	private static final Logger logger = LoggerFactory.getLogger(HelpCenterController.class);
	
	@Autowired
	HtmlServiceCacheWrapper htmlService;
	
	@RequestMapping("/about-us")
	public String aboutUs(Model model) {
		logger.debug("Go to about Us page");
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_ABOUT_US, "About Us");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "About Us");
		
		addPageMeta(model, "About Us", "About Us", "About Us");
		return "helpCenter";
	}
	
	@RequestMapping("/contact-us")
	public String contactUs(Model model) {
		logger.debug("Go to contact Us page");
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_CONTACT_US, "Contact Us");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Contact Us");
		
		addPageMeta(model, "Contact Us", "Contact Us", "Contact Us");
		return "helpCenter";
	}
	
	@RequestMapping("/customer-service")
	public String customerService(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_CUSTOMER_SERVICE, "Customer Service");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Customer Service");
		
		addPageMeta(model, "Customer Service", "Customer Service", "Customer Service");
		return "helpCenter";
	}
	
	@RequestMapping("/privacy-policies")
	public String privacyPolicies(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_PRIVACY_POLICIES, "Privacy Policies");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Privacy Policies");
		
		addPageMeta(model, "Privacy Policies", "Privacy Policies", "Privacy Policies");
		return "helpCenter";
	}
	
	@RequestMapping("/legal-window")
	public String legalWindow(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LEGAL_WINDOW, "Legal Window");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Legal Window");
		
		addPageMeta(model, "Legal Window", "Legal Window", "Legal Window");
		return "helpCenter";
	}
	
	@RequestMapping("/warranty-return")
	public String warrantyReturn(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_WARRANTY_AND_RETURN, "Warranty and Return");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Warranty and Return");
		model.addAttribute("page", "customerService");
		
		addPageMeta(model, "Warranty and Return", "Warranty and Return", "Warranty and Return");
		return "helpCenter";
	}
	
	@RequestMapping("/payment-methods")
	public String paymentMethods(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_PAYMENT_METHODS, "Payment Methods");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Payment Methods");
		model.addAttribute("page", "customerService");
		
		addPageMeta(model, "Payment Methods", "Payment Methods", "Payment Methods");
		return "helpCenter";
	}
	
	@RequestMapping("/terms-conditions")
	public String termsConditions(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_TERMS_AND_CONDITIONS, "Terms and Conditions");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Terms and Conditions");
		model.addAttribute("page", "customerService");
		
		addPageMeta(model, "Terms and Conditions", "Terms and Conditions", "Terms and Conditions");
		return "helpCenter";
	}
	
	@RequestMapping("/shipping-handling")
	public String shippingHandling(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_SHIPPING_HANDLING, "Shipping&Handling");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Shipping&Handling");
		model.addAttribute("page", "customerService");
		
		addPageMeta(model, "Shipping&Handling", "Shipping&Handling", "Shipping&Handling");
		return "helpCenter";
	}
	
	@RequestMapping("/testimonials")
	public String testimonials(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_TESTIMONIALS, "Testimonials");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Testimonials");
		model.addAttribute("page", "customerService");
		
		addPageMeta(model, "Testimonials", "Testimonials", "Testimonials");
		return "helpCenter";
	}
	
	@RequestMapping("/faq")
	public String faq(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_FAQ, "FQA");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "FQA");
		model.addAttribute("page", "myAccount");
		
		addPageMeta(model, "FQA", "FQA", "FQA");
		return "helpCenter";
	}
	
	@RequestMapping("/shipping-and-handling")
	public String shippingAndHandling(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_SHIPPING_HANDLING, "Shipping & Handling");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Shipping & Handling");
		model.addAttribute("page", "otherInfomation");
		
		addPageMeta(model, "Shipping & Handling", "Shipping & Handling", "Shipping & Handling");
		return "helpCenter";
	}
	
	@RequestMapping("/large-order-quotations")
	public String largeOrderQuotations(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LARGE_ORDER_QUOTATIONS, "Large Order Quotations");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Large Order Quotations");
		model.addAttribute("page", "otherInfomation");
		
		addPageMeta(model, "Large Order Quotations", "Large Order Quotations", "Large Order Quotations");
		return "helpCenter";
	}
	
	@RequestMapping("/lowest-price-guarantee")
	public String lowestPriceGuarantee(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LOWEST_PRICE_GUARANTEE, "Lowest Price Guarantee");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Lowest Price Guarantee");
		model.addAttribute("page", "otherInfomation");
		
		addPageMeta(model, "Lowest Price Guarantee", "Lowest Price Guarantee", "Lowest Price Guarantee");
		return "helpCenter";
	}
	
	@RequestMapping("/look-for-vendors")
	public String lookForVendors(Model model) {
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_LOOK_FOR_VENDORS, "Look for Vendors");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Look for Vendors");
		model.addAttribute("page", "otherInfomation");
		
		addPageMeta(model, "Look for Vendors", "Look for Vendors", "Look for Vendors");
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
