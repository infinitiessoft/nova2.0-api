package com.infinities.nova.api.exception;

public class FlavorNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public FlavorNotFoundException() {
		this(null);
	}

	public FlavorNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Flavor %s could not be found.";
	}

}
