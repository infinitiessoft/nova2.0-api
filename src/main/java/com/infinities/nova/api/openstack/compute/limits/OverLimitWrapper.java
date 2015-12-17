package com.infinities.nova.api.openstack.compute.limits;

import java.io.Serializable;

public class OverLimitWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OverLimit overLimit;


	public OverLimit getOverLimit() {
		return overLimit;
	}

	public void setOverLimit(OverLimit overLimit) {
		this.overLimit = overLimit;
	}

}
