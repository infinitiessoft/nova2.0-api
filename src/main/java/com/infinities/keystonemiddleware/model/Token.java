package com.infinities.keystonemiddleware.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.infinities.keystonemiddleware.model.adapter.ExpireDateAdapter;
import com.infinities.keystonemiddleware.model.adapter.IssueDateAdapter;

public class Token implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	@XmlJavaTypeAdapter(IssueDateAdapter.class)
	private Calendar issued_at;
	@XmlJavaTypeAdapter(ExpireDateAdapter.class)
	private Calendar expires;

	private Tenant tenant;

	private Bind bind;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getIssued_at() {
		return issued_at;
	}

	public void setIssued_at(Calendar issued_at) {
		this.issued_at = issued_at;
	}

	public Calendar getExpires() {
		return expires;
	}

	public void setExpires(Calendar expires) {
		this.expires = expires;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Bind getBind() {
		return bind;
	}

	public void setBind(Bind bind) {
		this.bind = bind;
	}

	// @Override
	// public String toString() {
	// return "Token [id=" + id + ", issued_at=" + issued_at + ", expires=" +
	// expires + ", tenant=" + tenant + "]";
	// }

}
