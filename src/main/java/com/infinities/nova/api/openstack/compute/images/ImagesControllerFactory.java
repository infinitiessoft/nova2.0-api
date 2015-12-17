package com.infinities.nova.api.openstack.compute.images;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.images.api.ImagesApi;

public class ImagesControllerFactory implements Factory<ImagesController> {

	private final ImagesApi imagesApi;


	@Inject
	public ImagesControllerFactory(ImagesApi imagesApi) {
		this.imagesApi = imagesApi;
	}

	@Override
	public ImagesController provide() {
		return new ImagesControllerImpl(imagesApi);
	}

	@Override
	public void dispose(ImagesController instance) {
	}

}
