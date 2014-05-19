/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.sf.ehcache.CacheManager;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.service.EmailService;
import com.hb.core.service.ProductService;
import com.hb.core.util.Constants;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
public class AdminController {
	
	@Autowired
	@Qualifier("hbCacheManager")
	private CacheManager cacheManager;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	EmailService emailService;

	@RequestMapping("/console")
	public String console() {

		return "admin_console";
	}

	@RequestMapping("/cache/list")
	public String listCache(Model model) throws IOException {
		model.addAttribute("cache", cacheManager.getCacheNames());
		
		return "admin_cache";
	}

	@RequestMapping("/cache/remove")
	public String removeCacheCache(Model model, @RequestParam("el") String cache)
			throws IOException {
		cacheManager.getCache(cache).removeAll();
		return "forward:/admin/cache/list";
	}
	
	@RequestMapping("/changetags")
	@ResponseBody
	public String changeTags() {
		productService.upcaseFirstLetterInTags();
		return "{status:success}";
	}
	
	public static String token = UUID.randomUUID().toString();
	@RequestMapping("/token")
	@ResponseBody
	public String token() {
		return token == null ? "" : token;
	}
	
	@RequestMapping(value="/token", method=RequestMethod.POST)
	@ResponseBody
	public String postToken(@RequestParam("value") String tokenValue) {
		if(!StringUtils.isEmpty(tokenValue)) {
			token = tokenValue;
		} 
		return token == null ? "" : token;
	}
	
	@RequestMapping("/edm")
	public String edm() throws IOException {
		
		return "edm";
	}
	
	@RequestMapping(value="/edm", method=RequestMethod.POST)
	public String postEdm(@RequestParam("subject") String subject,
			@RequestParam("message") String message,
			@RequestParam("emails") String emails,
			@RequestParam("emailSeparator") String emailSeparator,
			@RequestParam("emailHost") String emailHost,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("emailFrom") String emailFrom,
			@RequestParam("interval") String interval,
			Model model) throws IOException {
		String errorAttributeName = "errorMessage";
		if(StringUtils.isEmpty(subject)) {
			model.addAttribute(errorAttributeName, "Subject is empty");
		} else if(StringUtils.isEmpty(message)) {
			model.addAttribute(errorAttributeName, "Message is empty");
		} else if(StringUtils.isEmpty(emails)) {
			model.addAttribute(errorAttributeName, "Email list is empty");
		} else if(StringUtils.isEmpty(emailHost)) {
			model.addAttribute(errorAttributeName, "Email host is empty");
		} else if(StringUtils.isEmpty(username)) {
			model.addAttribute(errorAttributeName, "Email account is empty");
		} else if(StringUtils.isEmpty(password)) {
			model.addAttribute(errorAttributeName, "Password account is empty");
		}
		
		if(StringUtils.isEmpty(emailSeparator)) {
			emailSeparator = "\\s+";
		}
		String[] emailArray = emails.split(emailSeparator);
		if(emailArray.length > 0) {
			long period = 0L;
			try {
				period = Long.parseLong(interval);
				if(period < Constants.EDM_MIN_PERIOD) {
					period = Constants.EDM_MIN_PERIOD;
				}
			} catch(NumberFormatException e) {
				period = Constants.EDM_MIN_PERIOD;
			}
			List<String> emailList = Arrays.asList(emailArray);
			boolean sendFlag = emailService.sendEdmMail(subject, message, emailHost, username, password, emailFrom, emailList, period);
			if(!sendFlag) {
				model.addAttribute(errorAttributeName, "EDM Email task is not executed as there is a previous task ");
			}
		} else {
			model.addAttribute(errorAttributeName, "Emails size is empty");
		}
		
		return "edm";
	}
}
