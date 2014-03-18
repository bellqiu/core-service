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
	
	public static String upperFirstLetterEachWord(String str) {
		if(StringUtils.isEmpty(str)) {
			return str;
		}
		final StringBuilder result = new StringBuilder(str.length());
		String[] words = str.split("\\s");
		for(int i=0,l=words.length;i<l;++i) {
			String word = words[i].toLowerCase();
			if(i > 0) result.append(" ");      
			result.append(Character.toUpperCase(word.charAt(0)))
		        .append(word.substring(1).toLowerCase());

		}
		return result.toString();
	}
	
	public static String replaceSpace(String str, String replacement) {
		/*String newString = str.replaceAll("[^a-zA-Z0-9\\-]+","");
		newString = newString.replaceAll("\\-+", "-");
		return newString;*/
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		return str.replaceAll("\\s+", replacement);
	}
	
}
