/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honeybuy.shop.web.cache.ProductServiceCacheWraper;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class CategoryController {
	
	@Autowired
	private ProductServiceCacheWraper productService;
	
	@RequestMapping("/c/{categoryName}/{page:\\d+}")
	public String productDetail(@PathVariable("categoryName") String categoryName,@PathVariable("page") int page,  Model model){
		return "categoryIndex";
	}
	
	@RequestMapping("/c/{categoryName}")
	public String productDetail(@PathVariable("categoryName") String categoryName, Model model){
		return "forward:/c/"+categoryName+"/0";
	}
	
}
