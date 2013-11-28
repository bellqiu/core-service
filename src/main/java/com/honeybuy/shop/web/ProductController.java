/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Currency;
import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.ProductChangeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
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


	@Autowired
	private ProductService productServiceNoCache;
	
	@RequestMapping("/{productName}")
	public String productDetail(@PathVariable("productName") String productName, Model model, 
				@CookieValue(defaultValue="", required=false, value="productOpts")String cookieOpts, 
				@RequestParam(required=false, value="productOpts", defaultValue="") String paramOpts){
		
		String optParams = StringUtils.isEmpty(paramOpts)? cookieOpts : paramOpts;
		
		if(!productService.exist(productName)){
			return "404";
		}
		
		ProductDetailDTO productDetailDTO = productService.getProductDetailByName(productName);
		
		model.addAttribute("currentProductDetail", productDetailDTO);
		model.addAttribute("currentProductProductChange", productService.compupterProductChangeByOptsAndCurrency(productDetailDTO, optParams));
		
		return "productDetail";
	}
	
	@RequestMapping("/json/productChange/{productName}")
	@ResponseBody
	public ProductChangeDTO getProductDetailWithOptions(@PathVariable("productName") String productName, Model model, 
			@CookieValue(defaultValue="", required=false, value="productOpts")String cookieOpts, 
			@RequestParam(required=false, value="productOpts", defaultValue="") String paramOpts, HttpServletRequest request){
		
		String optParams = StringUtils.isEmpty(paramOpts)? cookieOpts : paramOpts;
		Currency currency = (Currency) request.getSession().getAttribute("defaultCurrency");
		
		if(!productService.exist(productName)){
			return new ProductChangeDTO();
		}
		
		ProductDetailDTO productDetailDTO = productServiceNoCache.getProductDetailByName(productName);
		ProductChangeDTO changeDTO = productServiceNoCache.compupterProductChangeByOptsAndCurrency(productDetailDTO, optParams);
		changeDTO.setPriceChange(changeDTO.getPriceChange() * currency.getExchangeRateBaseOnDefault());
		
		return changeDTO;
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
