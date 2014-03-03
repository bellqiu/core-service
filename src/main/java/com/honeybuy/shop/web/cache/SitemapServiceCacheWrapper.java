package com.honeybuy.shop.web.cache;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.service.CategoryService;
import com.hb.core.service.ProductService;
import com.hb.core.shared.dto.CategoryTreeDTO;
import com.hb.core.shared.dto.ProductChangeDTO;
import com.hb.core.shared.dto.ProductDetailDTO;
import com.hb.core.shared.dto.ProductSummaryDTO;
import com.hb.core.shared.dto.TabProductDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.CloneUtil;
import com.honeybuy.shop.web.HomeController;

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
	
	@Cacheable(cacheName="SitemapCategory")
	public String getCategoryXml(){
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
}
