package com.infinities.nova.api.exception;

public class InvalidRequestException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidRequestException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Invalid request is invalid.";
	}

}
