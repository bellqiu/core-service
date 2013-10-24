package com.honeybuy.shop.web.tag;

import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.shared.dto.CategoryTreeDTO;
import com.honeybuy.shop.web.cache.CategoryServiceCacheWraper;

public class CategoryTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	@Autowired
	private CategoryServiceCacheWraper categoryService;
	
	private String parentId;
	
	private String level;
	
	@Override
	public String handle(ServletRequest request) {
		int id = 0;
		
		if(!StringUtils.isEmpty(parentId) && parentId.matches("\\d+")){
			id = Integer.valueOf(parentId); 
		}
		
		List<CategoryTreeDTO> categoryTree = categoryService.getCategoryTree(id);
		request.setAttribute("catlogs", categoryTree);
		if("2".equals(level)) {
			return "catloglevle2";
		} else if("3".equals(level)) {
			return "catloglevle3";
		}
		return "catloglevle1";
	}
	
	@Override
	public void clean(ServletRequest request) {
		//request.removeAttribute("htmlContent");
	}
	
	@Override
	public void release() {
		parentId = null;
		level = null;
	}
	
	public CategoryServiceCacheWraper getSettingService() {
		return categoryService;
	}

	public void setCategoryService(CategoryServiceCacheWraper categoryService) {
		this.categoryService = categoryService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}
	
}
