package com.infinities.nova.api.exception;

public class ImageNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ImageNotFoundException() {
		this(null);
	}

	public ImageNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Image %s could not be found.";
	}

}
