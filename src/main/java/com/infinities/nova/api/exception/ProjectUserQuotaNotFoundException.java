package com.infinities.nova.api.exception;

public class ProjectUserQuotaNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ProjectUserQuotaNotFoundException() {
		this(null);
	}

	public ProjectUserQuotaNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Quota for user %s in project %s could not be found";
	}

}
