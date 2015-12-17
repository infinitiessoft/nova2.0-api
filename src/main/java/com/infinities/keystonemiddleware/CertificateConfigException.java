package com.infinities.keystonemiddleware;

public class CertificateConfigException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public CertificateConfigException() {
	}

	public CertificateConfigException(String message) {
		super(message);
	}

	public CertificateConfigException(Throwable cause) {
		super(cause);
	}

	public CertificateConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}
