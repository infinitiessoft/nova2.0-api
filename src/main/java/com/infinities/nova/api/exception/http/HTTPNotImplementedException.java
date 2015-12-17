package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPNotImplementedException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPNotImplementedException(String message) {
		super(message, Response.Status.NOT_IMPLEMENTED);
	}
}
