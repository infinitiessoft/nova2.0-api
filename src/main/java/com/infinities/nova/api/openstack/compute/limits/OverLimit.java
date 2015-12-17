package com.infinities.nova.api.openstack.compute.limits;

import java.io.Serializable;

public class OverLimit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
	private String detail;
	private long retryAfter;


	public OverLimit(int code, String message, String detail, long retryAfter) {
		super();
		this.code = code;
		this.message = message;
		this.detail = detail;
		this.retryAfter = retryAfter;
	}

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getRetryAfter() {
		return retryAfter;
	}

	public void setRetryAfter(long retryAfter) {
		this.retryAfter = retryAfter;
	}

}
