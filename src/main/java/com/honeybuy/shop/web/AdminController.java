/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.IOException;
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

import com.hb.core.service.ProductService;

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
}
