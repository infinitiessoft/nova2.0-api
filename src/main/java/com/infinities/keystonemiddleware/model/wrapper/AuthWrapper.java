package com.infinities.keystonemiddleware.model.wrapper;

import java.io.Serializable;

import com.infinities.keystonemiddleware.model.Auth;

public class AuthWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Auth auth;


	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

}
