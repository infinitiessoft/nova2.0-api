package com.infinities.nova.api.exception;

public class InvalidImageConfigDriveException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidImageConfigDriveException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Image's config drive option '%s' is invalid";
	}

}
