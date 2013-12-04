package com.honeybuy.shop.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamValueUtils {
	private static final Logger logger = LoggerFactory.getLogger(ParamValueUtils.class);
	public static Map<String, String> parseParamString(String param){
		Map<String,String> paramMap = new HashMap<String, String>();
		
		String[] paramsArray = param.split("&");
		
		for (String paramPair : paramsArray) {
			String[] pArr = paramPair.split("=");
			if(pArr.length > 1){
				try {
					paramMap.put(pArr[0], URLDecoder.decode(pArr[1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		return paramMap;
	}
}
