package com.honeybuy.shop.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hb.core.entity.HTML;
import com.honeybuy.shop.web.cache.HtmlServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;

public class BigAdd extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6291125879700376692L;
	
	@Autowired
	private SettingServiceCacheWrapper settingService;
	
	@Autowired
	private HtmlServiceCacheWrapper htmlService;
	
	private String settingKey;

	@Override
	public String handle(ServletRequest request) {
		String s = settingService.getStringValue(settingKey);
		
		List<HTML> htmls  = new ArrayList<HTML>();
		
		if(!StringUtils.isEmpty(s)){
			for (String k : s.split(",")) {
				HTML html = htmlService.getHTML(k);
				if(null!=html){
					htmls.add(html);
				}
			}
		}
		
		request.setAttribute("bigAddHtmls", htmls);
		
		return "bigAdd";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("bigAddHtmls");
	}
	
	@Override
	public void release() {
		settingKey = null;
	}
	
	public SettingServiceCacheWrapper getSettingService() {
		return settingService;
	}

	public void setSettingService(SettingServiceCacheWrapper settingService) {
		this.settingService = settingService;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

}
