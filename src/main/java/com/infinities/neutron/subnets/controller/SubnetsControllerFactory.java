package com.infinities.neutron.subnets.controller;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.neutron.subnets.api.SubnetsApi;

public class SubnetsControllerFactory implements Factory<SubnetsController>{

	private final SubnetsApi subnetApi;
	
	@Inject
	public SubnetsControllerFactory(SubnetsApi subnetApi) {
		this.subnetApi = subnetApi;
	}
	
	@Override
	public void dispose(SubnetsController arg0) {
		
	}

	@Override
	public SubnetsController provide() {
		return new SubnetsControllerImpl(subnetApi);
	}

}
