package com.infinities.neutron.subnets.model;

import java.io.Serializable;

public class SubnetTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Subnet subnet;


	/**
	 * @return the subnet
	 */
	public Subnet getSubnet() {
		return subnet;
	}

	/**
	 * @param subnet
	 *            the subnet to set
	 */
	public void setSubnet(Subnet subnet) {
		this.subnet = subnet;
	}
}
