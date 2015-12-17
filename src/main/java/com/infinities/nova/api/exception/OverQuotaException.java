package com.infinities.nova.api.exception;

public class OverQuotaException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public OverQuotaException() {
		this(null);
	}

	public OverQuotaException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Quota exceeded for resources: %s";
	}

}
