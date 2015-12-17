package com.infinities.keystonemiddleware.model.wrapper;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.infinities.keystonemiddleware.model.Token;

public class RevokedWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Token> revoked = new HashSet<Token>();


	public Set<Token> getRevoked() {
		return revoked;
	}

	public void setRevoked(Set<Token> revoked) {
		this.revoked = revoked;
	}

}
