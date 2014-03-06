package com.honeybuy.shop.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLCodingUtil {
	
	public static String encode(String value) throws UnsupportedEncodingException {
		if(value != null) {
			return URLEncoder.encode(value, "UTF-8");
		} 
		return null;
	}
	
	public static String decode(String value) throws UnsupportedEncodingException {
		if(value != null) {
			return URLDecoder.decode(value, "UTF-8");
		} 
		return null;
	}
}
