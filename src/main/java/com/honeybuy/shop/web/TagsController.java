package com.honeybuy.shop.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honeybuy.shop.web.cache.TagsServiceCacheWrapper;

@Controller
@RequestMapping("/tags")
public class TagsController {
	
	private static final Logger logger = LoggerFactory.getLogger(TagsController.class);
	
	@Autowired
	private TagsServiceCacheWrapper tagsService;
	
	public static List<String> INDEX_KEYS = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0-9");
	
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
		Set<String> tags = tagsService.getAllTagIndexMap().get(indexName);
		model.addAttribute("tags", tags);
		model.addAttribute("indexName", indexName);
		return "tagIndex";
	}
	
	@RequestMapping("/index/key/{indexName}")
	public String tagDetail(@PathVariable("indexName") String indexName,
			Model model){
		return "getAllTagIndexMap";
	}
	
	@RequestMapping("/index/key/{indexName}/{page:\\d+}")
	public String tagDetail(@PathVariable("indexName") String indexName,
			@PathVariable("page") int page,
			Model model){
		return "getAllTagIndexMap";
	}
	
}
