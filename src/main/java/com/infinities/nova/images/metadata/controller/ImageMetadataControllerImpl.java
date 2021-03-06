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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.exception.http.HTTPBadRequestException;
import com.infinities.api.openstack.commons.exception.http.HTTPNotFoundException;
import com.infinities.nova.common.model.MetaItemTemplate;
import com.infinities.nova.common.model.MetadataTemplate;
import com.infinities.nova.exception.ImageNotFoundException;
import com.infinities.nova.images.api.ImagesApi;
import com.infinities.nova.images.metadata.api.ImageMetadataApi;
import com.infinities.nova.images.model.Image;

public class ImageMetadataControllerImpl implements ImageMetadataController {

	// private final static Logger logger =
	// LoggerFactory.getLogger(ImageMetadataControllerImpl.class);
	private final ImagesApi imageApi;
	private final ImageMetadataApi metadataApi;


	public ImageMetadataControllerImpl(ImagesApi imagesApi, ImageMetadataApi metadataApi) {
		this.imageApi = imagesApi;
		this.metadataApi = metadataApi;
	}

	@Override
	public MetadataTemplate index(String imageId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Image image = getImage(context, imageId);
		Map<String, String> properties = image.getMetadata();
		MetadataTemplate metadata = new MetadataTemplate();
		metadata.setMetadata(properties);

		return metadata;
	}

	private Image getImage(OpenstackRequestContext context, String imageId) throws Exception {
		try {
			return imageApi.get(context, imageId);
		} catch (ImageNotFoundException e) {
			throw new HTTPNotFoundException("Image not found.");
		}
	}

	@Override
	public MetaItemTemplate show(String imageId, String key, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Image image = getImage(context, imageId);
		Map<String, String> properties = image.getMetadata();
		MetaItemTemplate template = new MetaItemTemplate();
		template.setMeta(new HashMap<String, String>());

		if (properties.containsKey(key)) {
			template.getMeta().put(key, properties.get(key));
			return template;
		} else {
			throw new HTTPNotFoundException();
		}
	}

	@Override
	public MetadataTemplate create(String imageId, MetadataTemplate metadata, ContainerRequestContext requestContext)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Image image = getImage(context, imageId);

		if (metadata.getMetadata() != null) {
			for (Entry<String, String> entry : metadata.getMetadata().entrySet()) {
				image.getMetadata().put(entry.getKey(), entry.getValue());
			}
		}
		metadataApi.create(context, imageId, metadata.getMetadata());
		MetadataTemplate ret = new MetadataTemplate();
		ret.setMetadata(image.getMetadata());
		return ret;
	}

	@Override
	public MetaItemTemplate update(String imageId, String key, MetaItemTemplate body, ContainerRequestContext requestContext)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");

		Map<String, String> meta = body.getMeta();
		if (meta == null) {
			String msg = "Incorrect request body format";
			throw new HTTPBadRequestException(msg);
		}

		if (!meta.containsKey(key)) {
			String msg = "Request body and URI mismatch";
			throw new HTTPBadRequestException(msg);
		}

		if (meta.size() > 1) {
			String msg = "Request body contains too many items";
			throw new HTTPBadRequestException(msg);
		}

		Image image = getImage(context, imageId);
		image.getMetadata().put(key, meta.get(key));

		metadataApi.update(context, imageId, key, meta);

		MetaItemTemplate ret = new MetaItemTemplate();
		ret.setMeta(meta);

		return ret;
	}

	@Override
	public Response delete(String imageId, String key, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Image image = getImage(context, imageId);

		if (!image.getMetadata().containsKey(key)) {
			String msg = "Invalid metadata key";
			throw new HTTPNotFoundException(msg);
		}

		image.getMetadata().remove(key);
		metadataApi.delete(context, imageId, key);
		return Response.noContent().build();
	}

	@Override
	public MetadataTemplate updataAll(String imageId, MetadataTemplate body, ContainerRequestContext requestContext)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Image image = getImage(context, imageId);
		Map<String, String> metadata = body.getMetadata();
		if (metadata == null) {
			metadata = new HashMap<String, String>();
		}
		image.setMetadata(metadata);
		metadataApi.updateAll(context, imageId, metadata);
		MetadataTemplate ret = new MetadataTemplate();
		ret.setMetadata(metadata);
		return ret;
	}

}
