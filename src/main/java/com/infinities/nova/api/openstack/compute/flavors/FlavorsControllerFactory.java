package com.infinities.nova.api.openstack.compute.flavors;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.flavors.api.FlavorsApi;

public class FlavorsControllerFactory implements Factory<FlavorsController> {

	private final FlavorsApi flavorsApi;


	@Inject
	public FlavorsControllerFactory(FlavorsApi flavorsApi) {
		this.flavorsApi = flavorsApi;
	}

	@Override
	public FlavorsController provide() {
		return new FlavorsControllerImpl(flavorsApi);
	}

	@Override
	public void dispose(FlavorsController instance) {
	}

}
