package com.infinities.nova.api.openstack.compute.servers;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.servers.api.ComputeApi;

public class ServersControllerFactory implements Factory<ServersController> {

	private final ComputeApi computesApi;


	@Inject
	public ServersControllerFactory(ComputeApi computesApi) {
		this.computesApi = computesApi;
	}

	@Override
	public ServersController provide() {
		return new ServersControllerImpl(computesApi);
	}

	@Override
	public void dispose(ServersController instance) {
	}

}
