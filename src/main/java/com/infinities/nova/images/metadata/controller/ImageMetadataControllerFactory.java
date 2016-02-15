/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.nova.images.metadata.controller;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.images.api.ImagesApi;
import com.infinities.nova.images.metadata.api.ImageMetadataApi;

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
