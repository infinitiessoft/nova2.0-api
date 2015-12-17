package com.infinities.keystonemiddleware.model;

import java.io.Serializable;

public class RevokedToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String signed;


	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

}
