package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPBadRequestException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPBadRequestException(String message) {
		super(message, Response.Status.BAD_REQUEST);
	}

}
