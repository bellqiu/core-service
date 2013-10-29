package com.honeybuy.shop.web.tag;

import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.shared.dto.CategoryTreeDTO;
import com.honeybuy.shop.web.cache.CategoryServiceCacheWrapper;

public class SpecialOfferTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	@Autowired
	private CategoryServiceCacheWrapper categoryService;
	
	private String parentId;
	
	@Override
	public String handle(ServletRequest request) {
		int id = 0;
		
		if(!StringUtils.isEmpty(parentId) && parentId.matches("\\d+")){
			id = Integer.valueOf(parentId); 
		}
		
		List<CategoryTreeDTO> categoryTree = categoryService.getSpecialCategoryTree(id);
		request.setAttribute("specialcatlogs", categoryTree);
		return "specialOffer";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("specialcatlogs");
	}
	
	@Override
	public void release() {
		parentId = null;
	}
	
	public CategoryServiceCacheWrapper getSettingService() {
		return categoryService;
	}

	public void setCategoryService(CategoryServiceCacheWrapper categoryService) {
		this.categoryService = categoryService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
