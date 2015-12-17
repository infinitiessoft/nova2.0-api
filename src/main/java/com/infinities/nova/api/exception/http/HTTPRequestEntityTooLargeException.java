package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPRequestEntityTooLargeException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPRequestEntityTooLargeException(String message) {
		super(message, Response.Status.REQUEST_ENTITY_TOO_LARGE);
	}

}
