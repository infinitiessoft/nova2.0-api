package com.infinities.nova.api.exception;

public class PortLimitExceededException extends QuotaError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PortLimitExceededException() {
		this(null);
	}

	public PortLimitExceededException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Maximum number of port exceeded";
	}

}
