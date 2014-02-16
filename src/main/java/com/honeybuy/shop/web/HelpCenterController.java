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
		
		model.addAttribute("pageTitle", "About Us");
		model.addAttribute("pageKeywords", "About Us");
		model.addAttribute("pageDescription", "About Us");
		return "helpCenter";
	}
	
	@RequestMapping("/contact-us")
	public String contactUs(Model model) {
		logger.debug("Go to contact Us page");
		String htmlContent = htmlService.getHTMLContent(Constants.HTML_CONTACT_US, "CONTACT Us");
		model.addAttribute("content", htmlContent);
		model.addAttribute("panelTitle", "Contact Us");
		
		PageMeta meta = new PageMeta();
		meta.setTitle("Contact Us");
		meta.setKeywords("Contact Us");
		meta.setDescription("Contact Us");
		model.addAttribute("pageMeta", meta);
		return "helpCenter";
	}

}
