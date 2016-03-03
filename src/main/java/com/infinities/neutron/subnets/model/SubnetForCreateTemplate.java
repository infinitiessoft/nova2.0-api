package com.infinities.neutron.subnets.model;

import java.io.Serializable;

public class SubnetForCreateTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SubnetForCreate subnet;

	/**
	 * @return the subnet
	 */
	public SubnetForCreate getSubnet() {
		return subnet;
	}

	/**
	 * @param subnet
	 *            the subnet to set
	 */
	public void setSubnet(SubnetForCreate subnet) {
		this.subnet = subnet;
	}

}
