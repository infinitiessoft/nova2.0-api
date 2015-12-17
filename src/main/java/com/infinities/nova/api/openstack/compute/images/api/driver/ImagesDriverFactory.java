package com.infinities.nova.api.openstack.compute.images.api.driver;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

public class ImagesDriverFactory implements Factory<ImagesDriver> {

	@Inject
	public ImagesDriverFactory() {

	}

	@Override
	public ImagesDriver provide() {
		return new ImagesDriverImpl();
	}

	@Override
	public void dispose(ImagesDriver instance) {

	}

}
