package com.honeybuy.shop.web.tag;

import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.service.SettingService;

public class BigAdd extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6291125879700376692L;
	@Autowired
	private SettingService settingService;
	
	private String settingKey;

	@Override
	public String handle(PageContext context) {
		String s = settingService.getStringValue("test");
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
