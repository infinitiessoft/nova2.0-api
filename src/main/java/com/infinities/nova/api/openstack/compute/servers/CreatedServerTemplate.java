package com.infinities.nova.api.openstack.compute.servers;

import java.io.Serializable;

public class CreatedServerTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CreatedServer server;


	public CreatedServerTemplate(CreatedServer server) {
		super();
		this.server = server;
	}

	public CreatedServer getServer() {
		return server;
	}

}
