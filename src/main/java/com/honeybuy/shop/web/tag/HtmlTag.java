package com.honeybuy.shop.web.tag;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.entity.HTML;
import com.honeybuy.shop.web.cache.HtmlServiceCacheWrapper;

public class HtmlTag extends AbstractHBTag{
	
	private static final long serialVersionUID = 4262739972508526664L;

	@Autowired
	private HtmlServiceCacheWrapper htmlService;
	
	private String htmlKey;
	
	private boolean ajax = false;

	@Override
	public String handle(ServletRequest request) {
		if(ajax) {
			return "htmlAjax";
		}
		HTML html = htmlService.getHTML(htmlKey);
		
		if(html != null) {
			request.setAttribute("htmlContent", html.getContent());
		}
		
		return "html";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("htmlContent");
	}
	
	@Override
	public void release() {
		htmlKey = null;
	}
	
	public HtmlServiceCacheWrapper getSettingService() {
		return htmlService;
	}

	public void setHtmlService(HtmlServiceCacheWrapper htmlService) {
		this.htmlService = htmlService;
	}

	public String getHtmlKey() {
		return htmlKey;
	}

	public void setHtmlKey(String htmlKey) {
		this.htmlKey = htmlKey;
	}
	
	public boolean getAjax() {
		return this.ajax;
	}
	
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

}
