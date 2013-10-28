package com.honeybuy.shop.web.tag;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.shared.dto.TabProductDTO;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;

public class TabedProduct extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6141222023233739141L;

	/**
	 * 
	 */
	
	@Autowired
	private SettingServiceCacheWrapper settingService;
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	private String tabKey;

	@Override
	public String handle(ServletRequest request) {
		TabProductDTO productDTO = productService.getTabProductByName(tabKey);
		
		request.setAttribute("tabProduct", productDTO);
		
		return "tabProduct";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("tabProduct");
	}
	
	@Override
	public void release() {
		setTabKey(null);
	}

	public String getTabKey() {
		return tabKey;
	}

	public void setTabKey(String tabKey) {
		this.tabKey = tabKey;
	}
	

}
