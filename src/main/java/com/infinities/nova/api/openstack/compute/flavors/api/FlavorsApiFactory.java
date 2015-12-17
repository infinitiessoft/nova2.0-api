package com.infinities.nova.api.openstack.compute.flavors.api;

import org.glassfish.hk2.api.Factory;

public class FlavorsApiFactory implements Factory<FlavorsApi> {

	@Override
	public FlavorsApi provide() {
		return new FlavorsApiImpl();
	}

	@Override
	public void dispose(FlavorsApi instance) {
	}

}
