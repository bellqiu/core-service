package com.honeybuy.shop.web.tag;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletRequest;

import com.honeybuy.shop.util.URLCodingUtil;

public class UrlUtil extends AbstractHBTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6141222023233739141L;
	
	private String value;
	
	private boolean decode;

	@Override
	public String handle(ServletRequest request) {
		String codingValue = null;
		try {
			if(decode) {
				codingValue = URLCodingUtil.decode(value);
			} else {
				codingValue = URLCodingUtil.encode(value);
			}
		} catch (Exception e) {
			codingValue = value;
		}
		if(codingValue == null) {
			if(value == null) {
				request.setAttribute("value", "");
			} else {
				request.setAttribute("value", value);
			}
		} else {
			request.setAttribute("value", codingValue);
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
