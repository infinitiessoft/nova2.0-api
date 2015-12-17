package com.infinities.keystonemiddleware.model.v3.wrapper;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlTransient;

import com.infinities.keystonemiddleware.model.Bind;
import com.infinities.keystonemiddleware.model.TokenWrapper;
import com.infinities.keystonemiddleware.model.v3.Token;

public class TokenV3Wrapper implements Serializable, TokenWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Token token;


	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	@XmlTransient
	public Calendar getExpire() {
		return token.getExpiresAt();
	}

	@Override
	@XmlTransient
	public Bind getBind() {
		return token.getBind();
	}

}
