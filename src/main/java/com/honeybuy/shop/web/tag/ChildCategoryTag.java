package com.honeybuy.shop.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.shared.dto.CategoryTreeDTO;
import com.honeybuy.shop.web.cache.CategoryServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;

public class ChildCategoryTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	@Autowired
	private CategoryServiceCacheWrapper categoryService;
	
	@Autowired
	private SettingServiceCacheWrapper settingService;
	
	private String settingKey;
	
	@Override
	public String handle(ServletRequest request) {
		String value = settingService.getStringValue(settingKey);
		List<String> parentNames = new ArrayList<String>();
		if(!StringUtils.isEmpty(value)){
			for (String k : value.split(",")) {
				parentNames.add(k);
			}
		}
		List<CategoryTreeDTO> categoryTree = categoryService.getSubCategoryName(parentNames);
		request.setAttribute("subCategory", categoryTree);
		return "subCategory";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("subCategory");
	}
	
	@Override
	public void release() {
		settingKey = null;
	}
	
	public CategoryServiceCacheWrapper getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryServiceCacheWrapper categoryService) {
		this.categoryService = categoryService;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

}
