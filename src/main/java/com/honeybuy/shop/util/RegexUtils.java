package com.honeybuy.shop.util;

import org.apache.cxf.common.util.StringUtils;

public class RegexUtils {
	
	public static String replaceSpecialChar(String str, String replacement) {
		/*String newString = str.replaceAll("[^a-zA-Z0-9\\-]+","");
		newString = newString.replaceAll("\\-+", "-");
		return newString;*/
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		return str.replaceAll("[\\W]+", replacement);
	}
	
}
