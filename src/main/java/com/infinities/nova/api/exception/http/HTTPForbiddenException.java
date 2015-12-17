package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPForbiddenException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPForbiddenException(String message) {
		super(message, Response.Status.FORBIDDEN);
	}
}
