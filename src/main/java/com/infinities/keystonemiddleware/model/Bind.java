package com.infinities.keystonemiddleware.model;

import java.io.Serializable;

import com.infinities.keystonemiddleware.model.bind.Kerberos;
import com.infinities.keystonemiddleware.model.bind.X509;

public class Bind implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Kerberos kerberos;
	private X509 x590;


	public Kerberos getKerberos() {
		return kerberos;
	}

	public void setKerberos(Kerberos kerberos) {
		this.kerberos = kerberos;
	}

	public X509 getX590() {
		return x590;
	}

	public void setX590(X509 x590) {
		this.x590 = x590;
	}

	public boolean isEmpty() {
		return kerberos == null && x590 == null;
	}

}
