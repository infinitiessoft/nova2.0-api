package com.infinities.nova.api.exception;

public class PolicyNotAuthorizedException extends ForbiddenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PolicyNotAuthorizedException() {
		super(null);
	}

	public PolicyNotAuthorizedException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Policy doesn't allow %s to be performed.";
	}

}
