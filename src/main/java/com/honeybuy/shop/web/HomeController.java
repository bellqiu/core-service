/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class HomeController {
	
	@RequestMapping("/home")
	public String home(){
		return "home";
	}
	
	
	@RequestMapping("/")
	public String defaultPage(){
		return "redirect:/home";
	}
	
	
	@RequestMapping("/iWantAnError")
	public String iWantAnError(){
		throw new RuntimeException();
	}
	
	@RequestMapping("/testJson")
	@ResponseBody
	public String test(){
		return "asfasfjkldgjdklfgjdfklgjdfklgjdfklg";
	}
}
