package com.honeybuy.shop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("")
public class FreedomPageController {
	private static final Logger logger = LoggerFactory.getLogger(FreedomPageController.class);
	
	@RequestMapping("/page/{pageName}")
	public String freedomPage(@PathVariable("pageName") String pageName, Model model) {
		logger.debug("Access freedom Page {}", pageName);
		model.addAttribute("freedompage", pageName);
		//
		/*File f = new File("src/main/webapp/WEB-INF/jsp/freedom_pages/");
		String str = "";
		if(f.exists()) {
			str="e ";
		}
		if(f.isDirectory()) {
			str +="D ";
		}
		model.addAttribute("path", str);*/
		return "freedomPage";
	}
	
}
