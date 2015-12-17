package com.infinities.keystonemiddleware.model.wrapper;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlTransient;

import com.infinities.keystonemiddleware.model.Access;
import com.infinities.keystonemiddleware.model.Bind;
import com.infinities.keystonemiddleware.model.TokenWrapper;

public class AccessWrapper implements Serializable, TokenWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Access access;


	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

	@Override
	@XmlTransient
	public Calendar getExpire() {
		return access.getToken().getExpires();
	}

	@Override
	@XmlTransient
	public Bind getBind() {
		return access.getToken().getBind();
	}

}
