package com.infinities.nova.api.exception;

public class InstanceUserDataTooLargeException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InstanceUserDataTooLargeException() {
		this(null);
	}

	public InstanceUserDataTooLargeException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "User data too large. User data must be no larger than %s bytes once base64 encoded. Your data is %d bytes";
	}

}
