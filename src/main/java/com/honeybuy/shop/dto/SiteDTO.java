/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.honeybuy.shop.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class SiteDTO {

	private List<String> css;
	private List<String> js;
	private String resourceServer;
	private String webResourcesFolder;
	private String productImageResourcesFolder;
	private Serializable ownerId;
	private String defaultCurrency;
	private String logo;
	private String siteName;
	private String domain;

	public SiteDTO() {
	}

	public List<String> getCss() {
		return css;
	}

	public void setCss(List<String> css) {
		this.css = css;
	}

	public List<String> getJs() {
		return js;
	}

	public void setJs(List<String> js) {
		this.js = js;
	}

	public Serializable getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Serializable ownerId) {
		this.ownerId = ownerId;
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public String getResourceServer() {
		return resourceServer;
	}

	public void setResourceServer(String resourceServer) {
		this.resourceServer = resourceServer;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}


	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getWebResourcesFolder() {
		return webResourcesFolder;
	}

	public void setWebResourcesFolder(String webResourcesFolder) {
		this.webResourcesFolder = webResourcesFolder;
	}

	public String getProductImageResourcesFolder() {
		return productImageResourcesFolder;
	}

	public void setProductImageResourcesFolder(
			String productImageResourcesFolder) {
		this.productImageResourcesFolder = productImageResourcesFolder;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}


}
