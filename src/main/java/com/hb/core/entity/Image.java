package com.hb.core.entity;


public class Image extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1698610860522003320L;
	
	private String name;
	
	private String altTitle;
	private String noChangeUrl;

	private String logoUrl;
	private String iconUrl;
	private String largerUrl;
	private String thumbnailUrl;
	private String smallUrl;
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
