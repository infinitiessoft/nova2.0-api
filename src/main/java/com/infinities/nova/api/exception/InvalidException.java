package com.infinities.nova.api.exception;

public class InvalidException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidException() {
		this(null);
	}

	public InvalidException(String message, Object... args) {
		super(400, message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Unacceptable parameters.";
	}

}
