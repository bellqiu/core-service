package com.honeybuy.shop.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.PageMetaUtils;
import com.honeybuy.shop.web.cache.TagsServiceCacheWrapper;

@Controller
@RequestMapping("/tags")
public class TagsController {
	
	private static final Logger logger = LoggerFactory.getLogger(TagsController.class);
	
	@Autowired
	private TagsServiceCacheWrapper tagsService;
	
	public static List<String> INDEX_KEYS = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0-9");
	
	public final static int TAG_PER_PAGE = 80;
	
	public final static int TAG_PRODUCT_PER_PAGE = 24;
	
	@RequestMapping("/index/{indexName}")
	public String tagIndex(@PathVariable("indexName") String indexName,
			Model model){
		return "forward:/tags/index/" + indexName + "/0";
	}
	
	@RequestMapping("/index/{indexName}/{page:\\d+}")
	public String tagIndex(@PathVariable("indexName") String indexName,
			@PathVariable("page") int page,
			Model model){
		if(StringUtils.isEmpty(indexName) || !INDEX_KEYS.contains(indexName)){
			logger.warn("Tag: {} is not existing", indexName);
			return "404";
		}
		int totalCount = tagsService.getTagsCountByIndex(indexName);
		int max = TAG_PER_PAGE;
		
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
		List<String> tags = tagsService.getTagsByIndexWithLimit(indexName, start, max);
		if(tags != null && tags.size() > 0) {
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
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("pageIds", pageIds);
			model.addAttribute("tags", tags);
		
		}
		model.addAttribute("indexName", indexName);
		model.addAttribute("currentPageIndex", page);
		
		PageMetaUtils.addPageMeta(model, "Index: " + indexName, indexName, indexName);
		return "tagIndex";
	}
	
	@RequestMapping("/key/{tagName:.*}")
	public String tagDetail(@PathVariable("tagName") String tagName,
			Model model){
		return "forward:/tags/key/" + tagName + "/0";
	}
	
	@RequestMapping("/key/{tagName:.*}/{page:\\d+}")
	public String tagDetail(@PathVariable("tagName") String tagName,
			@PathVariable("page") int page,
			Model model){
		if((tagName = tagsService.existTag(tagName)) == null){
			logger.warn("Tag: {} is not existing", tagName);
			return "404";
		}
		
		int totalCount = tagsService.getProductCountByTag(tagName);
		int max = TAG_PRODUCT_PER_PAGE;
		
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
		List<ProductSummaryDTO> productList = tagsService.getProductByTagWithLimit(tagName, start, max);
		if(productList != null && productList.size() > 0) {
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
			model.addAttribute("resultEnd", start + productList.size());
			model.addAttribute("resultTotal", totalCount);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("pageIds", pageIds);
			model.addAttribute("products", productList);
		
		}
		String indexName;
		if(Character.isDigit(tagName.charAt(0))) {
			indexName = TagsServiceCacheWrapper.TAG_DIGITAL_INDEX;
		} else {
			indexName = tagName.substring(0, 1).toUpperCase();
		}
		model.addAttribute("tagName", tagName);
		model.addAttribute("indexName", indexName);
		model.addAttribute("currentPageIndex", page);
		
		model.addAttribute("leftproducts", tagsService.getTabProductByName(Constants.TAB_PRODUCT_TAG_LEFT_PANEL));
		
		PageMetaUtils.addPageMeta(model, "Tag Product for " + tagName, tagName, tagName);
		
		return "tagDetail";
	}
	
}