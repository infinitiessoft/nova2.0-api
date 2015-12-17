package com.infinities.keystonemiddleware;

public class NetworkException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public NetworkException() {
	}

	public NetworkException(String message) {
		super(message);
	}

	public NetworkException(Throwable cause) {
		super(cause);
	}

	public NetworkException(String message, Throwable cause) {
		super(message, cause);
	}

}
