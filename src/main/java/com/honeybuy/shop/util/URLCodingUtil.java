package com.honeybuy.shop.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hb.core.util.Constants;

public class URLCodingUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(URLCodingUtil.class);
	
	public static String encode(String value) {
		if(value != null) {
			value = encodeSlash(value);
			try {
				return URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.warn("URLEncode error for {}", value);
				return value;
			}
		} 
		return null;
	}
	
	public static String decode(String value) {
		if(value != null) {
			value = decodeSlash(value);
			try {
				return URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.warn("URLDecode error for {}", value);
				return value;
			}
		} 
		return null;
	}
	
	public static String encodeSlash(String value) {
		if(value != null) {
			if(value.contains(Constants.SLASH)) {
				return value.replace(Constants.SLASH, Constants.SLASH_REPLACEMENT);
			} else {
				return value;
			}
		} 
		return null;
	}
	
	public static String decodeSlash(String value) {
		if(value != null) {
			if(value.contains(Constants.SLASH_REPLACEMENT)) {
				return value.replace(Constants.SLASH_REPLACEMENT, Constants.SLASH);
			} else {
				return value;
			}
		} 
		return null;
	}
}
