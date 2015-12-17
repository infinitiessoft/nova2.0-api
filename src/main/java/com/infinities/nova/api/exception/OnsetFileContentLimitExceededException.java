package com.infinities.nova.api.exception;

public class OnsetFileContentLimitExceededException extends QuotaError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public OnsetFileContentLimitExceededException() {
		this(null);
	}

	public OnsetFileContentLimitExceededException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Personality file content too long";
	}

}
