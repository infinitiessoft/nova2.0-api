package com.infinities.nova.api.exception;

public class InvalidMetadataSizeException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidMetadataSizeException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Invalid metadata size: %s";
	}

}
