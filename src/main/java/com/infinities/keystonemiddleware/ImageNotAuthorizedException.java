package com.infinities.keystonemiddleware;

public class ImageNotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public ImageNotAuthorizedException() {
	}

	public ImageNotAuthorizedException(String message) {
		super(message);
	}

	public ImageNotAuthorizedException(Throwable cause) {
		super(cause);
	}

	public ImageNotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

}
