package com.infinities.keystonemiddleware.model;

import java.io.Serializable;

public class Auth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PasswordCredentials passwordCredentials;
	private String tenantName;


	public PasswordCredentials getPasswordCredentials() {
		return passwordCredentials;
	}

	public void setPasswordCredentials(PasswordCredentials passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

}
