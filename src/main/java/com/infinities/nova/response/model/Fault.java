package com.infinities.nova.response.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Fault implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	@XmlElement(nillable = false)
	private Object retryAfter;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getRetryAfter() {
		return retryAfter;
	}

	public void setRetryAfter(Object retryAfter) {
		this.retryAfter = retryAfter;
	}

}
