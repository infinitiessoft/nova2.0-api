package com.infinities.nova.api.exception;

public class QuotaNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public QuotaNotFoundException() {
		this(null);
	}

	public QuotaNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Quota could not be found";
	}

}
