package com.infinities.nova.api.openstack.compute.images.api;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.images.api.driver.ImagesDriver;

public class ImagesApiFactory implements Factory<ImagesApi> {

	private final ImagesDriver driver;


	@Inject
	public ImagesApiFactory(ImagesDriver driver) {
		this.driver = driver;
	}

	@Override
	public ImagesApi provide() {
		return new ImagesApiImpl(driver);
	}

	@Override
	public void dispose(ImagesApi instance) {
	}

}
