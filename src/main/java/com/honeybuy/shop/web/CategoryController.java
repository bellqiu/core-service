/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Currency;
import com.hb.core.shared.dto.CategoryDetailDTO;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.shared.dto.TabProductDTO;
import com.hb.core.util.Constants;
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
	
	private final static int CATEGORY_PRODUCT_PER_PAGE = 24; 
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	@Autowired
	private CategoryServiceCacheWrapper categoryService;
	
	@RequestMapping("/c/{categoryName}/{page:\\d+}")
	public String productDetail(
			@PathVariable("categoryName") String categoryName,
			@PathVariable("page") int page,
			@RequestParam(value="low", required=false) Double lowPrice,
			@RequestParam(value="high", required=false) Double highPrice,
			Model model,
			HttpSession session){
		CategoryDetailDTO categoryDetailDTO =  categoryService.getCategoryDetailByName(categoryName);
		if(null == categoryDetailDTO){
			return "404";
		}
		
		long categoryId = categoryDetailDTO.getId();
		
		int totalCount = -1;
		double newLowPrice = -1;
		double newHighPrice = -1;
		Currency currency = (Currency) session.getAttribute("defaultCurrency");
		float rateBaseOnDefault = currency.getExchangeRateBaseOnDefault();
		if(lowPrice == null || lowPrice == null) {
			totalCount = productService.getProductCountByCategoryId(categoryId);
		} else {
			newLowPrice = lowPrice / rateBaseOnDefault;
			newHighPrice = highPrice / rateBaseOnDefault;
			totalCount = productService.getProductCountWithPriceRangeByCategoryId(categoryId, newLowPrice, newHighPrice);
		}
		int max = CATEGORY_PRODUCT_PER_PAGE;
		
		int totalPage;
		if(totalCount % max == 0) {
			totalPage = totalCount / max;
		} else {
			totalPage = totalCount / max + 1;
		}
		int start = page * max;
		if(start >= totalCount) {
			page = 0;
			start = 0;
		}
		List<ProductSummaryDTO> productSummaryList;
		if(lowPrice == null || lowPrice == null) {
			productSummaryList = productService.getAllProductByCategoryId(categoryId, start, max);
		} else {
			productSummaryList = productService.getAllProductWithPriceRangeByCategoryId(categoryId, newLowPrice, newHighPrice, start, max);
		}
		
		if(productSummaryList.size() > 0) {
			productService.setLikeSold(productSummaryList);
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
			model.addAttribute("products", productSummaryList);
		} 
		model.addAttribute("currentCategoryDetail", categoryDetailDTO);
		model.addAttribute("currentPageIndex", page);
		
		/*String paraStr = "";
		int lowestPrice = trimDoubleToInt(productService.getLowestPriceByCategoryId(categoryId) * rateBaseOnDefault, true);
		int highestPrice = trimDoubleToInt(productService.getHighestPriceByCategoryId(categoryId) * rateBaseOnDefault, false);
		model.addAttribute("lowestPrice", lowestPrice);
		model.addAttribute("highestPrice", highestPrice);
		if(lowPrice == null || highPrice == null) {
			model.addAttribute("currentLowestPrice", lowestPrice);
			model.addAttribute("currentHighestPrice", highestPrice);
		} else {
			int clp = trimDoubleToInt(lowPrice, true);
			int chp = trimDoubleToInt(highPrice, false);
			if(clp <= lowestPrice) {
				clp = lowestPrice;
			} 
			if(chp >= highestPrice) {
				chp = highestPrice;
			}
			if(clp != lowestPrice || chp != highestPrice) {
				paraStr = "?low=" + clp + "&high=" + chp;
			}
			model.addAttribute("currentLowestPrice", clp);
			model.addAttribute("currentHighestPrice", chp);
		}
		model.addAttribute("pStr", paraStr);
		*/
		
		List<CategoryTreeDTO> categoryBreadcrumb = categoryService.getCategoryBreadcrumb(categoryId);
		model.addAttribute("categoryBreadcrumbs", categoryBreadcrumb);
		
		List<CategoryTreeDTO> subCateogries = categoryService.getSubCategories(categoryId);
		model.addAttribute("subCateogries", subCateogries);
		
		TabProductDTO leftPanelTabProduct = productService.getTabProductByName(Constants.CATEGORY_PRODUCT_TAG_LEFT_PANEL);
		if(leftPanelTabProduct != null) {
			model.addAttribute("leftproducts", leftPanelTabProduct.getProducts());
		}
		
		return "categoryIndex";
	}
	
	@RequestMapping("/c/{categoryName}")
	public String productDetail(@PathVariable("categoryName") String categoryName,
			@RequestParam(value="low", required=false) String lowPrice,
			@RequestParam(value="high", required=false) String highPrice,
			Model model){
		if(lowPrice != null && highPrice != null) {
			return "forward:/c/"+categoryName+"/0?low=" + lowPrice + "&high=" + highPrice;
		}
		return "forward:/c/"+categoryName+"/0";
	}
	
	public int trimDoubleToInt(double value, boolean floor) {
		if(value <= 0.0) {
			return 0;
		}
		if(floor) {
			return (int)Math.floor(value);
		} else {
			return (int)Math.ceil(value);
		}
	}
	
	/*@RequestMapping("/seach/c/test")
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
	}*/
	
	@RequestMapping(value="/fragment/json/updateCategory", method=RequestMethod.POST)
	@ResponseBody
	@Produces("application/xml")
	@Consumes("application/xml")
	@Transactional
	public String updateCategory(@BeanParam CategoryDetailDTO category,
			@RequestParam(value = "token", required = true) final String token){
		if(AdminController.token != null && AdminController.token.equals(token)) {
			boolean status = categoryService.updateCategory(category);
			if(status == true) {
				return "{\"status\":\"success\"}";
			}
		}
		return "{\"status\":\"fail\"}";
	}
	
}
