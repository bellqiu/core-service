/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.web;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.core.entity.Currency;
import com.hb.core.entity.Product;
import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.ProductChangeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.shared.dto.TabProductDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.JsonUtil;
import com.honeybuy.shop.util.RegexUtils;
import com.honeybuy.shop.web.cache.CategoryServiceCacheWrapper;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.dto.PageMeta;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductServiceCacheWrapper productService;

	@Autowired
	private ProductService productServiceNoCache;
	
	@Autowired
	private CategoryServiceCacheWrapper categoryService;
	
	private final static int SEARCH_PRODUCT_PER_PAGE = 24; 
	
	@RequestMapping("/{productName}")
	public String productDetail(@PathVariable("productName") String productName, Model model, 
				@CookieValue(defaultValue="", required=false, value="productOpts")String cookieOpts, 
				@RequestParam(required=false, value="productOpts", defaultValue="") String paramOpts){
		
		String optParams = StringUtils.isEmpty(paramOpts)? cookieOpts : paramOpts;
		
		if(!productService.exist(productName)){
			logger.warn("Product: {} is not existing", productName);
			return "404";
		}
		
		ProductDetailDTO productDetailDTO= productService.getProductDetailByName(productName);
		ProductChangeDTO changeDTO = productService.getProductDetailChangeByNameAndParams(productName, optParams);
		model.addAttribute("currentProductDetail", productDetailDTO);
		model.addAttribute("currentProductProductChange", changeDTO);
		model.addAttribute("currentProductOptions", optParams);
		
		PageMeta meta = new PageMeta();
		meta.setTitle(productDetailDTO.getTitle());
		meta.setKeywords(productDetailDTO.getKeywords());
		meta.setDescription(productDetailDTO.getAbstractText());
		model.addAttribute("pageMeta", meta);
		
		long id = productDetailDTO.getId();
		int like = productServiceNoCache.getLikesByProductId(id);
		model.addAttribute("likeNum", like);
		
		int sold = productServiceNoCache.getSoldsByProductId(id);
		model.addAttribute("soldNum", sold);
		
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
		
		ProductChangeDTO changeDTO = productService.getProductDetailChangeByNameAndParams(productName, optParams);
		
		logger.info("getPrice Change, priceChange={}", changeDTO.getPriceChange());
		
		changeDTO.setPriceChange(changeDTO.getPriceChange() * currency.getExchangeRateBaseOnDefault());
		
		
		return changeDTO;
	}
	
	@RequestMapping("/search")
	public String productSearch(
			@RequestParam(value = "keyword", required = false) final String keyword,
			@RequestParam(value = "page", required = false) final String page,
			Model model){
		if(!StringUtils.isEmpty(keyword)) {
			long id = 0;
			try {
				id = Long.valueOf(keyword);
			} catch(NumberFormatException e) {
			}
			if(id > 0) {
				ProductSummaryDTO productSummary = productService.getProductSummaryById(id);
				if(productSummary != null) {
					return "redirect:/" + productSummary.getName();
				}
			}
			String keyHyper = RegexUtils.replaceSpecialChar(keyword, Constants.HYPHEN_CHAR);
			if(!keyword.equalsIgnoreCase(keyHyper)) {
				return "redirect:/search?keyword=" + keyHyper;
			}
			String cateogryName = categoryService.queryCategoryName(keyHyper);
			if(cateogryName != null) {
				return "redirect:/c/" + cateogryName;
			}
			int pageId = 0;
			if(!StringUtils.isEmpty(page)) {
				try {
					pageId = Integer.valueOf(pageId);
				} catch(NumberFormatException e) {
				}
			}
			int totalCount = productService.searchProductCountByKey(keyHyper);
			int max = SEARCH_PRODUCT_PER_PAGE;
			
			int totalPage;
			if(totalCount % max == 0) {
				totalPage = totalCount / max;
			} else {
				totalPage = totalCount / max + 1;
			}
			int start = pageId * max;
			if(start >= totalCount) {
				pageId = 0;
				start = 0;
			}
			List<ProductSummaryDTO> productSummaryList = productService.searchProductByKey(keyHyper, start, max);
			
			if(productSummaryList.size() > 0) {
				List<Integer> pageIds = new ArrayList<Integer>();
				if(totalPage <= 7) {
					for(int i = 0; i < totalPage; i++) {
						pageIds.add(i);
					}
				} else {
					if(pageId < 3) {
						for(int i = 0; i < 5; i++) {
							pageIds.add(i);
						}
						pageIds.add(-1);
						pageIds.add(totalPage - 1);
					} else if(pageId >= (totalPage - 3)) {
						pageIds.add(0);
						pageIds.add(-1);
						for(int i = totalPage - 5; i < totalPage; i++) {
							pageIds.add(i);
						}
					} else {
						pageIds.add(0);
						pageIds.add(-1);
						for(int i = -1; i <= 1; i++) {
							pageIds.add(pageId + i);
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
			} 
			model.addAttribute("currentPageIndex", pageId);
			
			/*double lowestPrice = productService.getLowestPriceByCategoryId(categoryId);
			double highestPrice = productService.getHighestPriceByCategoryId(categoryId);
			model.addAttribute("lowestPrice", lowestPrice);
			model.addAttribute("highestPrice", highestPrice);*/
			String keySpace = RegexUtils.replaceSpecialChar(keyword, Constants.SPACE_CHAR).trim();
			keySpace = RegexUtils.upperFirstLetterEachWord(keySpace);
			model.addAttribute("keySpace", keySpace);
			model.addAttribute("keyword", keyHyper);
			
			TabProductDTO leftPanelTabProduct = productService.getTabProductByName(Constants.SEARCH_PRODUCT_TAG_LEFT_PANEL);
			if(leftPanelTabProduct != null) {
				model.addAttribute("leftproducts", leftPanelTabProduct.getProducts());
			}
			
			return "searchProduct";
		} else {
			return "redirect:/";
		}
	}
	
	private static final long LIKE_EXPIRE_MILSECONDS = 600000;
	@RequestMapping(value="/json/changeLike", method=RequestMethod.POST)
	@ResponseBody
	public int changeLikeService(@RequestParam(value = "id", required = false) final String id,
			HttpSession session) {
		if(StringUtils.isEmpty(id)) {
			return 0;
		}
		@SuppressWarnings("unchecked")
		Map<Long, Long> likeMap = (Map<Long, Long>)session.getAttribute("likeMap");
		if(likeMap == null) {
			likeMap = new HashMap<Long, Long>();
		}
		try {
			long idNumber = Long.parseLong(id);
			Long time = likeMap.get(idNumber);
			long currentTime = System.currentTimeMillis();
			if(time != null && (time + LIKE_EXPIRE_MILSECONDS) >= currentTime) {
				return 0;
			}
			likeMap.put(idNumber, currentTime);
			session.setAttribute("likeMap", likeMap);
			return productServiceNoCache.addLike(idNumber);
		} catch(NumberFormatException e) {
			logger.debug("{} can not be cast to long number.", id);
		} 
		return 0;
	}
	
	@RequestMapping(value="/fragment/json/uploadProduct", method=RequestMethod.POST)
	@ResponseBody
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Object uploadProduct(//@BeanParam Product product,
			@RequestParam(value = "token", required = true) final String token,
			HttpServletRequest request,
			HttpServletResponse response){
		if(AdminController.token != null && AdminController.token.equals(token)) {
			try {
				BufferedReader reader = request.getReader();
				/*String line;
				while((line = reader.readLine()) != null) {
					System.out.println(line);
				}*/
				Product product = JsonUtil.convertJson(reader, Product.class);
				Object detail = productServiceNoCache.uploadProduct(product);
				if(detail != null) {
					return detail;
				}
			} catch (Exception e) {
				logger.error("Exception when uploading product by json ", e);
			}
			return "{\"status\":\"fail\"}";
		} else {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
			return "";
		}
	}
	
}
