package com.infinities.keystonemiddleware.model.bind;

import java.io.Serializable;

public class X509 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fingerprint;
	private String algorithm;


	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

}
