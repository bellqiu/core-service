package com.hb.core.exception;

import java.util.UUID;

public class CoreServiceException extends RuntimeException{

	private String id;
	
	{
		
		setId(UUID.randomUUID().toString());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4955247855906774824L;

	public CoreServiceException() {
		super();
	}

	public CoreServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoreServiceException(String message) {
		super(message);
	}

	public CoreServiceException(Throwable cause) {
		super(cause);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
