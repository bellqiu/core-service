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

import com.hb.core.shared.dto.CategoryDetailDTO;
import com.honeybuy.shop.web.cache.CategoryServiceCacheWrapper;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class CategoryController {
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	@Autowired
	private CategoryServiceCacheWrapper categoryService;
	
	@RequestMapping("/c/{categoryName}/{page:\\d+}")
	public String productDetail(@PathVariable("categoryName") String categoryName,@PathVariable("page") int page,  Model model){
		CategoryDetailDTO categoryDetailDTO =  categoryService.getCategoryDetailByName(categoryName);
		if(null == categoryDetailDTO){
			return "404";
		}
		
		model.addAttribute("currentCategoryDetail", categoryDetailDTO);
		
		return "categoryIndex";
	}
	
	@RequestMapping("/c/{categoryName}")
	public String productDetail(@PathVariable("categoryName") String categoryName, Model model){
		return "forward:/c/"+categoryName+"/0";
	}
	
}
