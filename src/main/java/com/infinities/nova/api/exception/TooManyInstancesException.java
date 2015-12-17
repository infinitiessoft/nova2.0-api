package com.infinities.nova.api.exception;

public class TooManyInstancesException extends QuotaError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TooManyInstancesException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Quota exceeded for %s: Requested %s, but already used %s of %s %s";
	}

}
