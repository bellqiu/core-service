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
public class ProductController {
	
	@Autowired
	private ProductServiceCacheWraper productService;
	
	@RequestMapping("/{productName}")
	public String productDetail(@PathVariable("productName") String productName, Model model){
		
		if(!productService.exist(productName)){
			return "404";
		}
		
		model.addAttribute("currentProductDetail", productService.getProductDetailByName(productName));
		
		return "productDetail";
	}
	
}
