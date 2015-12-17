package com.infinities.nova.api.exception;

public class InvalidFixedIpAndMaxCountRequestException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidFixedIpAndMaxCountRequestException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Failed to launch instances: %s";
	}

}
