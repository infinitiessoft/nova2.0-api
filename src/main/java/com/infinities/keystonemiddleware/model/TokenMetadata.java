package com.infinities.keystonemiddleware.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;

public class TokenMetadata {

	private String id;
	private Calendar expires;
	@XmlElement(name = "user_id")
	private String userid;
	@XmlElement(name = "tenant_id")
	private String tenantid;
	@XmlElement(name = "domain_id")
	private String domainid;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getExpires() {
		return expires;
	}

	public void setExpires(Calendar expires) {
		this.expires = expires;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public void setDomainId(String domainid) {
		this.domainid = domainid;
	}

	public String getDomain() {
		return domainid;
	}

}
