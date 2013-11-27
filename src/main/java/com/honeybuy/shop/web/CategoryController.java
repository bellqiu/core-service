/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.shared.dto.CategoryDetailDTO;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
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
		
		long categoryId = categoryDetailDTO.getId();
		int totalCount = productService.getProductCountByCategoryId(categoryId);
		int max = 24;
		
		int totalPage = totalCount / max + 1;
		int start = page * max;
		if(start >= totalCount) {
			page = 0;
			start = 0;
		}
		List<ProductSummaryDTO> productSummaryList = productService.getAllProductByCategoryId(categoryId, start, max);
		
		if(productSummaryList.size() > 0) {
			List<Integer> pageIds = new ArrayList<Integer>();
			if(totalPage <= 7) {
				for(int i = 0; i < totalPage; i++) {
					pageIds.add(i);
				}
			} else {
				if(page < 3) {
					for(int i = 0; i < 5; i++) {
						pageIds.add(i);
					}
					pageIds.add(-1);
					pageIds.add(totalPage - 1);
				} else if(page >= (totalPage - 3)) {
					pageIds.add(0);
					pageIds.add(-1);
					for(int i = totalPage - 5; i < totalPage; i++) {
						pageIds.add(i);
					}
				} else {
					pageIds.add(0);
					pageIds.add(-1);
					for(int i = -1; i <= 1; i++) {
						pageIds.add(page + i);
					}
					pageIds.add(-1);
					pageIds.add(totalPage - 1);
				}
			}
			model.addAttribute("resultStart", start + 1);
			model.addAttribute("resultEnd", start + productSummaryList.size());
			model.addAttribute("resultTotal", totalCount);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("pageIds", pageIds);
			model.addAttribute("productSummary", productSummaryList);
			model.addAttribute("noproduct", false);
		} else {
			model.addAttribute("noproduct", true);
		}
		model.addAttribute("currentCategoryDetail", categoryDetailDTO);
		model.addAttribute("currentCategoryPageIndex", page);
		
		
		double lowestPrice = productService.getLowestPriceByCategoryId(categoryId);
		double highestPrice = productService.getHighestPriceByCategoryId(categoryId);
		model.addAttribute("lowestPrice", lowestPrice);
		model.addAttribute("highestPrice", highestPrice);
		
		List<String> categoryBreadcrumb = categoryService.getCategoryBreadcrumb(categoryId);
		model.addAttribute("categoryBreadcrumbs", categoryBreadcrumb);
		
		List<CategoryTreeDTO> subCateogries = categoryService.getSubCategories(categoryId);
		model.addAttribute("subCateogries", subCateogries);
		
		return "categoryIndex";
	}
	
	@RequestMapping("/c/{categoryName}")
	public String productDetail(@PathVariable("categoryName") String categoryName, Model model){
		return "forward:/c/"+categoryName+"/0";
	}
	
	@RequestMapping("/seach/c/test")
	public String searchCategoryProductList(Model model){
		return "categoryProductList";
	}
	
	@RequestMapping(value = "/ajax/c/{categoryName}", method = RequestMethod.GET)
	@ResponseBody
	public String getProductNames(
			@PathVariable("categoryName") String categoryName,
			@RequestParam(value = "startWith", required = false) final String startWith,
			@RequestParam(value = "callback", required = false) final String callback,
			@RequestParam(value = "key", required = false) final String key) {
		if(StringUtils.isEmpty(startWith) || StringUtils.isEmpty(callback) || StringUtils.isEmpty(key)) {
			return null;
		}
		CategoryDetailDTO categoryDetailDTO =  categoryService.getCategoryDetailByName(categoryName);
		if(null == categoryDetailDTO){
			return null;
		}
		long categoryId = categoryDetailDTO.getId();
		List<String> productName = productService.getProductName(categoryId, key, startWith);
		if(productName == null) {
			productName = new ArrayList<String>();
			//return null;
		}
		productName.add("aaaa");
		productName.add("bbbb");
		productName.add("cccc");
		StringBuffer sb = new StringBuffer(callback);
		sb.append("(");
		try {
			ObjectMapper mapper = new ObjectMapper();
			sb.append(mapper.writeValueAsString(productName));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		sb.append(");");
		return sb.toString();
	}
	
}
