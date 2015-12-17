package com.infinities.nova.api.exception.http;

import javax.ws.rs.core.Response;

public class HTTPConflictException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HTTPConflictException(String message) {
		super(message, Response.Status.CONFLICT);
	}

}
