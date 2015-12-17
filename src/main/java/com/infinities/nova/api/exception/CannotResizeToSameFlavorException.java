package com.infinities.nova.api.exception;

public class CannotResizeToSameFlavorException extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CannotResizeToSameFlavorException() {
		this(null);
	}

	public CannotResizeToSameFlavorException(String message, Object... args) {
		super(message, args);
	}

	@Override
	public String getMsgFmt() {
		return "When resizing, instances must change flavor!";
	}
}
