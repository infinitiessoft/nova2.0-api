package com.infinities.nova.api.openstack.compute.servers.metadata;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.servers.api.ComputeApi;

public class ServerMetadataControllerFactory implements Factory<ServerMetadataController> {

	private final ComputeApi computesApi;


	@Inject
	public ServerMetadataControllerFactory(ComputeApi computesApi) {
		this.computesApi = computesApi;
	}

	@Override
	public ServerMetadataController provide() {
		return new ServerMetadataControllerImpl(computesApi);
	}

	@Override
	public void dispose(ServerMetadataController instance) {
	}

}
