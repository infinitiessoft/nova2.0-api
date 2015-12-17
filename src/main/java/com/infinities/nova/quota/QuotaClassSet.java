package com.infinities.nova.quota;

import com.infinities.nova.response.model.QuotaSet;

public class QuotaClassSet extends QuotaSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String className;


	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
