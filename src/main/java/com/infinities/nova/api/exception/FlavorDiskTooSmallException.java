package com.infinities.nova.api.exception;

public class FlavorDiskTooSmallException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public FlavorDiskTooSmallException() {
		this(null);
	}

	public FlavorDiskTooSmallException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Flavor's disk is too small for requested image.";
	}

}
