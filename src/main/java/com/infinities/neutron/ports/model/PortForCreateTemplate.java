package com.infinities.neutron.ports.model;

import java.io.Serializable;


public class PortForCreateTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PortForCreate port;

	/**
	 * @return the port
	 */
	public PortForCreate getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(PortForCreate port) {
		this.port = port;
	}

}
