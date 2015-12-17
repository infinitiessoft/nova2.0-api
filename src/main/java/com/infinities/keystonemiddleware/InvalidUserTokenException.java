package com.infinities.keystonemiddleware;


public class InvalidUserTokenException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidUserTokenException() {
	}

	public InvalidUserTokenException(String message) {
		super(message);
	}

	public InvalidUserTokenException(Throwable cause) {
		super(cause);
	}

	public InvalidUserTokenException(String message, Throwable cause) {
		super(message, cause);
	}

}
