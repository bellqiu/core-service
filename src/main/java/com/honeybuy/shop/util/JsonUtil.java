package com.honeybuy.shop.util;

import java.io.Reader;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static Object getJsonFromURL(URL url) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object value = mapper.readValue(url, Object.class);
		return value;
	}
	
	public static Object getJsonFromString(String content) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object value = mapper.readValue(content, Object.class);
		return value;
	}
	
	public static <T> T convertJson(String content, Class<T> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T value = mapper.readValue(content, clazz);
		return value;
	}
	
	public static <T> T convertJson(Reader reader, Class<T> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T value = mapper.readValue(reader, clazz);
		return value;
	}

}
