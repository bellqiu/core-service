/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Currency;
import com.hb.core.entity.Option;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.honeybuy.shop.util.ParamValueUtils;
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
	public String productDetail(@PathVariable("productName") String productName, Model model, 
				@CookieValue(defaultValue="", required=false, value="productOpts")String cookieOpts, 
				@RequestParam(required=false, value="productOpts", defaultValue="") String paramOpts,
				@ModelAttribute("defaultCurrency") Currency currency){
		
		if(!productService.exist(productName)){
			return "404";
		}
		
		model.addAttribute("currentProductDetail", getProductDetailWithOptions(productName,model,cookieOpts,paramOpts, currency));
		
		return "productDetail";
	}
	
	@RequestMapping("/json/{productName}")
	@ResponseBody
	public ProductDetailDTO getProductDetailWithOptions(@PathVariable("productName") String productName, Model model, 
			@CookieValue(defaultValue="", required=false, value="productOpts")String cookieOpts, 
			@RequestParam(required=false, value="productOpts", defaultValue="") String paramOpts,
			@ModelAttribute("defaultCurrency") Currency currency
			){
		
		Map<String, String> paramMap = new HashMap<String, String>();
 		
		if(!StringUtils.isEmpty(paramOpts)){
			paramMap = ParamValueUtils.parseParamString(paramOpts);
		}else if(!StringUtils.isEmpty(cookieOpts)){
			paramMap = ParamValueUtils.parseParamString(cookieOpts);
		}
		
		ProductDetailDTO detailDTO = productService.getProductDetailByName(productName);
		
		
		detailDTO = productService.compupterProductByOptsAndCurrency(detailDTO, paramMap, currency);
		
		
		return detailDTO;
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
