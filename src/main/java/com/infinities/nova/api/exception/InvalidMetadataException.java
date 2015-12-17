package com.infinities.nova.api.exception;

public class InvalidMetadataException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidMetadataException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Invalid metadata: %s";
	}

}
