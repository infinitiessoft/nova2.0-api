package com.infinities.nova.api.exception;

public class InstanceIsLockedException extends InstanceInvalidStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InstanceIsLockedException() {
		this(null, null);
	}

	public InstanceIsLockedException(String message, Boolean notLaunched, Object... args) {
		super(message, notLaunched, args);
	}

	@Override
	public String getMsgFmt() {
		return "Instance %s is locked.";
	}

}
