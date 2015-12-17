package com.infinities.nova.api.exception;

public class InvalidInputException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidInputException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Invalid input received %s.";
	}

}
