package com.infinities.nova.api.exception;

public class QuotaClassNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public QuotaClassNotFoundException() {
		this(null);
	}

	public QuotaClassNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Quota class %s could not be found.";
	}

}
