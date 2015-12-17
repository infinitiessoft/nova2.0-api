package com.infinities.nova.api.exception;

public class InvalidImageRefException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidImageRefException(String message, Object... args) {
		super(400, message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Invalid image href %s.";
	}

}
