package com.honeybuy.shop.web.cache;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.CategoryService;
import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.URLCodingUtil;
import com.honeybuy.shop.web.HomeController;
import com.honeybuy.shop.web.TagsController;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional(readOnly=true)
public class SitemapServiceCacheWrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(SitemapServiceCacheWrapper.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TagsServiceCacheWrapper tagsService;
	
	@Cacheable(cacheName="SitemapCategory")
	public String getCategoryXml(){
		logger.debug("Get Category Sitemap Xml");
		List<CategoryTreeDTO> categories = categoryService.getAllCategories();
		InputStream in = HomeController.class.getResourceAsStream("/category.hpl");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("categories", categories);
		try {
			Template tpl = new Template("categorySiteMap", new InputStreamReader(in), new Configuration());
			StringWriter writer = new StringWriter();
			tpl.process(variables, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}

	@Cacheable(cacheName="SitemapProduct")
	public String getProductXml() {
		logger.debug("Get Product Sitemap Xml");
		List<String> productsName = productService.getAllProductsName();
		InputStream in = HomeController.class.getResourceAsStream("/product.hpl");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("names", productsName);
		try {
			Template tpl = new Template("productSiteMap", new InputStreamReader(in), new Configuration());
			StringWriter writer = new StringWriter();
			tpl.process(variables, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}

	public String getTgasXml() {
		logger.debug("Get Tags Sitemap Xml");
		TreeMap<String, Set<Long>> allTagsProductMap = tagsService.getAllTagsProductMap();
		List<String> allTagsName = new ArrayList<String>(allTagsProductMap.size() * 3);
		for(String tagName : allTagsProductMap.keySet()) {
			String tagRelativeURL = URLCodingUtil.encode(tagName.replace(Constants.SPACE_CHAR, Constants.HYPHEN_CHAR));
			allTagsName.add(tagRelativeURL);
			Set<Long> productsByTag = allTagsProductMap.get(tagName);
			if(productsByTag != null && productsByTag.size() > 0) {
				int totalPage = productsByTag.size() / TagsController.TAG_PRODUCT_PER_PAGE;
				for(int i = 0; i <= totalPage; i++) {
					allTagsName.add(tagRelativeURL + "/" + i);
				}
			}
		}
		InputStream in = HomeController.class.getResourceAsStream("/tags.hpl");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("indexName", TagsController.INDEX_KEYS);
		variables.put("tagsName", allTagsName);
		try {
			Template tpl = new Template("tagsSiteMap", new InputStreamReader(in), new Configuration());
			StringWriter writer = new StringWriter();
			tpl.process(variables, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}
}
