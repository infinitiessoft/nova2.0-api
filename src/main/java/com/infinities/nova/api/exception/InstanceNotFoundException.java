package com.infinities.nova.api.exception;

public class InstanceNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InstanceNotFoundException() {
		this(null);
	}

	public InstanceNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Instance %s could not be found.";
	}

}
