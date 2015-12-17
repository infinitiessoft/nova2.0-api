package com.infinities.nova.api.openstack.compute.images.api;

import org.glassfish.hk2.api.Factory;

public class ImageMetadataApiFactory implements Factory<ImageMetadataApi> {

	public ImageMetadataApiFactory() {

	}

	@Override
	public ImageMetadataApi provide() {
		return new ImageMetadataApiImpl();
	}

	@Override
	public void dispose(ImageMetadataApi instance) {
	}

}
