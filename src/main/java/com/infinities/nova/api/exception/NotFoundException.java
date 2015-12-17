package com.infinities.nova.api.exception;

public class NotFoundException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public NotFoundException() {
		this(null);
	}

	public NotFoundException(String message, Object... args) {
		super(404, message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Resource could not be found.";
	}

}
