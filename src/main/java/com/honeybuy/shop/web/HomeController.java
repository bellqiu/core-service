/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honeybuy.shop.web.cache.CategoryServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SitemapServiceCacheWrapper;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class HomeController {
	
	@Autowired
	private CategoryServiceCacheWrapper categoryService;
	
	@Autowired
	private SitemapServiceCacheWrapper sitemapService;
	
	@RequestMapping("/home")
	public String home(){
		return "home";
	}
	
	@RequestMapping("/")
	public String defaultPage(){
		return "redirect:/home";
	}
	
	
	@RequestMapping("/loging")
	public String loging(){
		return "loging";
	}
	
	@RequestMapping("/404")
	public String notFount(){
		return "404";
	}
	
	@RequestMapping("/500")
	public String serverError(){
		return "500";
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
	
	@RequestMapping("/sitemap.xml")
	@ResponseBody
	@Produces("application/xml")
	public String sitemap(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/xml");
		return "<xml>ljsl</xml>";
	}
	
	@RequestMapping("/category.xml")
	@ResponseBody
	@Produces("application/xml")
	public String categorySitemap(HttpServletRequest request, HttpServletResponse response){
		return sitemapService.getCategoryXml();
	}
	
	@RequestMapping("/p1.xml")
	@ResponseBody
	@Produces("application/xml")
	public String productSitemap(HttpServletRequest request, HttpServletResponse response){
		return sitemapService.getProductXml();
	}
	
	@RequestMapping("/tags.xml")
	@ResponseBody
	@Produces("application/xml")
	public String tagsSitemap(HttpServletRequest request, HttpServletResponse response){
		return sitemapService.getTgasXml();
	}
}
