package com.infinities.nova.api.exception;

public class MarkerNotFoundException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MarkerNotFoundException() {
		this(null);
	}

	public MarkerNotFoundException(String message, Object... args) {
		super(404, message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Marker %s could not be found.";
	}

}
