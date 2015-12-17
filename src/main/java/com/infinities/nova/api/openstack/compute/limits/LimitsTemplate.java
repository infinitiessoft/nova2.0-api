package com.infinities.nova.api.openstack.compute.limits;

import java.io.Serializable;

import com.infinities.nova.response.model.Limits;

public class LimitsTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Limits limits;


	public LimitsTemplate() {

	}

	public LimitsTemplate(Limits limits) {
		this.limits = limits;
	}

	public Limits getLimits() {
		return limits;
	}

	public void setLimits(Limits limits) {
		this.limits = limits;
	}
}
