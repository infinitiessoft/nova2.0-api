package com.infinities.nova.api.exception;

public class ImageNotActiveException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ImageNotActiveException() {
		this(null);
	}

	public ImageNotActiveException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Image %s is not active.";
	}

}
