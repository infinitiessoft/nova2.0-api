package com.infinities.nova.api.exception;

public class FlavorNotFoundByNameException extends FlavorNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public FlavorNotFoundByNameException() {
		this(null);
	}

	public FlavorNotFoundByNameException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Flavor %s with name %s could not be found.";
	}

}
