package com.hb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonAutoDetect
public class Image extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1698610860522003320L;
	
	@Column(name="name")
	private String name;
	
	@Column(name="alt_title")
	private String altTitle;
	
	@Column(name="no_change_url")
	private String noChangeUrl;

	@Column(name="logo_url")
	private String logoUrl;
	
	@Column(name="icon_url")
	private String iconUrl;
	
	@Column(name="larger_url")
	private String largerUrl;
	
	@Column(name="thumbnail_url")
	private String thumbnailUrl;
	
	@Column(name="small_url")
	private String smallUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type ;
	
	public static enum Type{
		LOGO,
		PRODUCT_NORMAL,
		ICON
	}
	
	public Image() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAltTitle() {
		return altTitle;
	}

	public void setAltTitle(String altTitle) {
		this.altTitle = altTitle;
	}

	public String getNoChangeUrl() {
		return noChangeUrl;
	}

	public void setNoChangeUrl(String noChangeUrl) {
		this.noChangeUrl = noChangeUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getLargerUrl() {
		return largerUrl;
	}

	public void setLargerUrl(String largerUrl) {
		this.largerUrl = largerUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
