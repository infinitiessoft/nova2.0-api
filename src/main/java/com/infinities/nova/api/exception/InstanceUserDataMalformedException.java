package com.infinities.nova.api.exception;

public class InstanceUserDataMalformedException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InstanceUserDataMalformedException() {
		this(null);
	}

	public InstanceUserDataMalformedException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "User data needs to be valid base 64.";
	}

}
