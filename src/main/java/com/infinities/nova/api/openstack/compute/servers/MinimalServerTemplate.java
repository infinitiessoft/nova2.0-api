package com.infinities.nova.api.openstack.compute.servers;

import java.io.Serializable;

public class MinimalServerTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MinimalServer server;


	public MinimalServerTemplate(MinimalServer server) {
		super();
		this.server = server;
	}

	public MinimalServer getServer() {
		return server;
	}

}
