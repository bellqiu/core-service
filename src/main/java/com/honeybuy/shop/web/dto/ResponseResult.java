/**
 * 
 */
package com.honeybuy.shop.web.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spark Zhu, wan-shan.zhu@hp.com
 *
 */
public class ResponseResult<T> {
	private boolean success = true;
	private Map<String, String> messageMap= new HashMap<String,String>();
	private T result;
	
	/**
	 * 
	 */
	public ResponseResult() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ResponseResult(boolean success, T result) {
		super();
		this.success = success;
		this.result = result;
	}



	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, String> getMessageMap() {
		return messageMap;
	}
	public void setMessageMap(Map<String, String> messageMap) {
		this.messageMap = messageMap;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	
}
