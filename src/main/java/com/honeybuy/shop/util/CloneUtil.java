package com.honeybuy.shop.util;

import java.io.StringReader;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CloneUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CloneUtil.class);
	
	@SuppressWarnings("unchecked")
	public static  <T> T cloneThroughJson(T t) {
		
		if(t == null){
			return null;
		}
		
		StringWriter stringWriter = new StringWriter();
		
		ObjectMapper mapper = new ObjectMapper();
		 mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		StringBuffer json = stringWriter.getBuffer();
		
		T rs = null;
		
	
		try {
			mapper.writeValue(stringWriter, t);
			rs = (T) mapper.readValue(new StringReader(json.toString()), t.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		
		
		logger.debug("Cloned object, Type={}, json={}", new Object[]{t.getClass().getName(), json});
		
		
		return  rs ;
		
	}
}
