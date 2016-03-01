package com.infinities.neutron.networks.model;

import java.io.Serializable;

public class NetworkTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Network network;


	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * @param network
	 *            the network to set
	 */
	public void setNetwork(Network network) {
		this.network = network;
	}

}
