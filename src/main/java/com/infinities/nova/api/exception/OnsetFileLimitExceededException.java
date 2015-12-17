package com.infinities.nova.api.exception;

public class OnsetFileLimitExceededException extends QuotaError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public OnsetFileLimitExceededException() {
		this(null);
	}

	public OnsetFileLimitExceededException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Personality file limit exceeded";
	}

}
