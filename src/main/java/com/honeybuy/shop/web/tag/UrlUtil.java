package com.honeybuy.shop.web.tag;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletRequest;

public class UrlUtil extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6141222023233739141L;
	
	private String value;
	
	private boolean decode;

	@Override
	public String handle(ServletRequest request) {
		if(value == null) {
			request.setAttribute("value", value);
		} else {
			try {
				if(decode) {
					request.setAttribute("value", URLDecoder.decode(value, "UTF-8"));
				} else {
					request.setAttribute("value", URLEncoder.encode(value, "UTF-8"));
				}
			} catch (Exception e) {
				request.setAttribute("value", value);
			}
		}
		return "urlUtil";
	}
	
	@Override
	public void clean(ServletRequest request) {
		request.removeAttribute("value");
	}
	
	@Override
	public void release() {
		setValue(null);
		decode = false;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDecode(boolean decode) {
		this.decode = decode;
	}

	public boolean isDecode() {
		return decode;
	}

}
