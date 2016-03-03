package com.infinities.neutron.networks.controller;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.neutron.networks.api.NetworksApi;


public class NetworksControllerFactory implements Factory<NetworksController>{

	private final NetworksApi networkApi;
	
	@Inject
	public NetworksControllerFactory(NetworksApi networkApi) {
		this.networkApi = networkApi;
	}
	
	@Override
	public void dispose(NetworksController arg0) {
		
	}

	@Override
	public NetworksController provide() {
		return new NetworksControllerImpl(networkApi);
	}

}
