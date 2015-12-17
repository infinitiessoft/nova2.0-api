package com.infinities.nova.api.exception;

import java.util.ArrayList;
import java.util.List;

public class QuotaError extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public QuotaError() {
		this(null);
	}

	public QuotaError(String message, Object... args) {
		super(403, message, args);
		List<Object> list = new ArrayList<Object>();
		list.add(0);
		this.getHeaders().put("Retry-After", list);
		this.setSafe(true);
	}

	@Override
	public String getMsgFmt() {
		return "Quota exceeded: code=%s";
	}

}
