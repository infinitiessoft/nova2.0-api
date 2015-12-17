package com.infinities.nova.api.openstack.compute.servers.ips;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.servers.api.ComputeApi;

public class ServerIpsControllerFactory implements Factory<ServerIpsController> {

	private final ComputeApi computesApi;


	@Inject
	public ServerIpsControllerFactory(ComputeApi computesApi) {
		this.computesApi = computesApi;
	}

	@Override
	public ServerIpsController provide() {
		return new ServerIpsControllerImpl(computesApi);
	}

	@Override
	public void dispose(ServerIpsController instance) {
	}

}
