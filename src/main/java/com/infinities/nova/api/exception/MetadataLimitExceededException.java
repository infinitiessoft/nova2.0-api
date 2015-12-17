package com.infinities.nova.api.exception;

public class MetadataLimitExceededException extends QuotaError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MetadataLimitExceededException() {
		this(null);
	}

	public MetadataLimitExceededException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "Maximum number of metadata items exceeds %s";
	}

}
