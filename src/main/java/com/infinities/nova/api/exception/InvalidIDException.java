package com.infinities.nova.api.exception;

public class InvalidIDException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidIDException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Invalid ID received %s.";
	}

}
