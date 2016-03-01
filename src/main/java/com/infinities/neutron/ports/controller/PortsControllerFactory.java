package com.infinities.neutron.ports.controller;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.neutron.ports.api.PortsApi;

public class PortsControllerFactory implements Factory<PortsController>{

	private final PortsApi portApi;
	
	@Inject
	public PortsControllerFactory(PortsApi portApi) {
		this.portApi = portApi;
	}
	
	@Override
	public void dispose(PortsController arg0) {
		
	}

	@Override
	public PortsController provide() {
		return new PortsControllerImpl(portApi);
	}

}
