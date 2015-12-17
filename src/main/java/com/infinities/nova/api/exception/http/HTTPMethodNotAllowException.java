package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPMethodNotAllowException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPMethodNotAllowException(String message) {
		super(message, Response.Status.METHOD_NOT_ALLOWED);
	}
	
	public HTTPMethodNotAllowException() {
		this(null);
	}
}
