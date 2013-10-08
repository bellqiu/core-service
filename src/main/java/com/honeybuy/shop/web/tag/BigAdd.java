package com.honeybuy.shop.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hb.core.entity.HTML;
import com.hb.core.service.HtmlService;
import com.hb.core.service.SettingService;

public class BigAdd extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6291125879700376692L;
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private HtmlService htmlService;
	
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

	public SettingService getSettingService() {
		return settingService;
	}

	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

}
