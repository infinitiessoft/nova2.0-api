package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPUnauthorizedException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPUnauthorizedException(String message) {
		super(message, Response.Status.UNAUTHORIZED);
	}

}
