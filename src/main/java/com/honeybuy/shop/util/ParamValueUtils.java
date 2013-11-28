package com.honeybuy.shop.util;

import java.util.HashMap;
import java.util.Map;

public class ParamValueUtils {
	public static Map<String, String> parseParamString(String param){
		Map<String,String> paramMap = new HashMap<String, String>();
		
		String[] paramsArray = param.split("&");
		
		for (String paramPair : paramsArray) {
			String[] pArr = paramPair.split("=");
			if(pArr.length > 1){
				paramMap.put(pArr[0], pArr[1]);
			}
		}
		
		return paramMap;
	}
}
