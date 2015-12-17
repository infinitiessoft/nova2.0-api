package com.infinities.nova.api.openstack.compute.images;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.images.api.ImageMetadataApi;
import com.infinities.nova.api.openstack.compute.images.api.ImagesApi;

public class ImageMetadataControllerFactory implements Factory<ImageMetadataController> {

	private final ImagesApi imagesApi;
	private final ImageMetadataApi metadataApi;


	@Inject
	public ImageMetadataControllerFactory(ImagesApi imagesApi, ImageMetadataApi metadataApi) {
		this.imagesApi = imagesApi;
		this.metadataApi = metadataApi;
	}

	@Override
	public ImageMetadataController provide() {
		return new ImageMetadataControllerImpl(imagesApi, metadataApi);
	}

	@Override
	public void dispose(ImageMetadataController instance) {
	}

}
