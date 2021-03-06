package com.honeybuy.shop.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.hb.core.entity.HTML;
import com.hb.core.service.HtmlService;
import com.honeybuy.shop.util.CloneUtil;

@Service
public class HtmlServiceCacheWrapper {
	
	@Autowired
	private HtmlService htmlService;
	
	@Cacheable(cacheName="html")
	public HTML getHTML(String key){
		HTML html= htmlService.getHTML(key);
		return CloneUtil.<HTML>cloneThroughJson(html);
	}
	
	public String getHTMLContent(String key){
		HTML html= getHTML(key);
		return html == null ? null : html.getContent();
	}
	
	public String getHTMLContent(String key, String defaultContent){
		HTML html= getHTML(key);
		if(html == null || html.getContent() == null) {
			return defaultContent;
		}
		return html.getContent();
	}
}
