package com.honeybuy.shop.web.cache;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.key.CacheKeyGenerator;
@Component("keyGenerator")
public class KeyGenerator implements CacheKeyGenerator<Serializable>{

	@Override
	public Serializable generateKey(MethodInvocation methodInvocation) {
		String className = methodInvocation.getClass().getName();
		String methodName = methodInvocation.getMethod().getName();

		Object[] object = methodInvocation.getArguments();
		
		String key = className+"#"+methodName;

		if(null != object){
			for (Object arg : object) {
				key += arg==null ? "#null" : "#"+arg.toString();
			}
		}
		
		return key;
	}

	@Override
	public Serializable generateKey(Object... data) {
		return null;
	}

}
