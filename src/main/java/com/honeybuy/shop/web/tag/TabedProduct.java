package com.honeybuy.shop.web.tag;

import javax.servlet.ServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.shared.dto.TabProductDTO;
import com.honeybuy.shop.web.cache.ProductServiceCacheWrapper;

public class TabedProduct extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6141222023233739141L;
	
	@Autowired
	private ProductServiceCacheWrapper productService;
	
	private String tabKey;
	
	private String jspPage;

	@Override
	public String handle(ServletRequest request) {
		TabProductDTO productDTO = productService.getTabProductByName(tabKey);
		if(productDTO != null) {
			productService.setLikeSold(productDTO.getProducts());
		}
		
		request.setAttribute("tabProduct", productDTO);
		
		if(!StringUtils.isEmpty(jspPage)) {
			return jspPage;
		}
		
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

	public void setJspPage(String jspPage) {
		this.jspPage = jspPage;
	}

	public String getJspPage() {
		return jspPage;
	}
	

}
