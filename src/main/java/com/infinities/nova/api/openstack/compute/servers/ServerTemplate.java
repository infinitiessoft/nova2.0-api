package com.infinities.nova.api.openstack.compute.servers;

import java.io.Serializable;

import com.infinities.nova.response.model.Server;

public class ServerTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Server server;


	public ServerTemplate() {

	}

	public ServerTemplate(Server server) {
		super();
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

}
