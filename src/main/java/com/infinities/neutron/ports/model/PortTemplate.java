package com.infinities.neutron.ports.model;

import java.io.Serializable;

public class PortTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Port port;


	/**
	 * @return the port
	 */
	public Port getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(Port port) {
		this.port = port;
	}
	
}
