/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
public class AdminController {
	@RequestMapping("/cacheManager")
	public String cacheManager(){
		
		return "cacheManager";
	}
	
	@RequestMapping("/console")
	public String console(){
		
		return "admin_console";
	}
}
