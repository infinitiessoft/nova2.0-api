package com.infinities.nova.api.exception;

public class ProjectQuotaNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ProjectQuotaNotFoundException() {
		this(null);
	}

	public ProjectQuotaNotFoundException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Quota for project %s could not be found";
	}

}
