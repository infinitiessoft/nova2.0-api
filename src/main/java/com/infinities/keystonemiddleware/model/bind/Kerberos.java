package com.infinities.keystonemiddleware.model.bind;

import java.io.Serializable;

public class Kerberos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String principal;


	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}
