package com.honeybuy.shop.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FeaturedObjectMapper extends ObjectMapper{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5104761797024230067L;

	public FeaturedObjectMapper() {
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
