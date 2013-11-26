/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hb.core.entity.Product;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class ProductController {
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	@RequestMapping("/{productName}")
	public String productDetail(@PathVariable("productName") String productName, Model model){
		
		if(!productService.exist(productName)){
			return "404";
		}
		
		model.addAttribute("currentProductDetail", productService.getProductDetailByName(productName));
		
		return "productDetail";
	}
	
	@RequestMapping("/search/product")
	public String productSearch(
			@RequestParam(value = "searchValue", required = false) final String keyword,
			@RequestParam(value = "page", required = false) final String page,
			Model model){
		
		if(StringUtils.isEmpty(keyword)) {
			return "404";
		}
		int pageId = 0;
		if(!StringUtils.isEmpty(page)) {
			try {
				pageId = Integer.valueOf(pageId);
			} catch(NumberFormatException e) {
			}
		}
		int max = 24;
		int start = pageId * max;
		int totalCount = productService.searchProductCountByKey(keyword);
		if(totalCount == 0) {
			
		}
		model.addAttribute("searchProducts", productService.searchProductByKey(keyword, start, max));
		
		return "searchProduct";
	}
	
}
