package com.infinities.nova.api.exception;

public class ForbiddenException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ForbiddenException() {
		this(null);
	}

	public ForbiddenException(String message, Object... args) {
		super(403, message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Not authorized.";
	}

}
