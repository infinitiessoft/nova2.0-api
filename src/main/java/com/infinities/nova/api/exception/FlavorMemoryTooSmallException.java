package com.infinities.nova.api.exception;

public class FlavorMemoryTooSmallException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public FlavorMemoryTooSmallException() {
		this(null);
	}

	public FlavorMemoryTooSmallException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Flavor's memory is too small for requested image.";
	}

}
