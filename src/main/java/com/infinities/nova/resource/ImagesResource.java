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
package com.infinities.nova.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infinities.nova.common.model.MetaItemTemplate;
import com.infinities.nova.common.model.MetadataTemplate;
import com.infinities.nova.exception.http.HTTPMethodNotAllowException;
import com.infinities.nova.images.controller.ImagesController;
import com.infinities.nova.images.metadata.controller.ImageMetadataController;
import com.infinities.nova.images.model.ImageTemplate;
import com.infinities.nova.images.model.ImagesTemplate;
import com.infinities.nova.images.model.MinimalImagesTemplate;
import com.infinities.nova.security.CheckProjectId;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
public class ImagesResource {

	private final ImagesController controller;
	private final ImageMetadataController metadataController;


	/**
	 * @param controller
	 * @param metadataController
	 */
	@Autowired
	public ImagesResource(ImagesController controller, ImageMetadataController metadataController) {
		super();
		this.controller = controller;
		this.metadataController = metadataController;
	}

	@GET
	public MinimalImagesTemplate index(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext) throws Exception {
		return controller.index(requestContext);
	}

	@GET
	@Path("detail")
	public ImagesTemplate datail(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		return controller.detail(requestContext);
	}

	@GET
	@Path("{imageId}")
	public ImageTemplate show(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@Context ContainerRequestContext requestContext) throws Exception {
		return controller.show(imageId, requestContext);
	}

	@DELETE
	@Path("{imageId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@Context ContainerRequestContext requestContext) throws Exception {
		return controller.delete(imageId, requestContext);
	}

	@POST
	public Response create() throws Exception {
		throw new HTTPMethodNotAllowException();
	}

	@GET
	@Path("{imageId}/metadata")
	public MetadataTemplate indexMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@Context ContainerRequestContext requestContext) throws Exception {
		return metadataController.index(imageId, requestContext);
	}

	@GET
	@Path("{imageId}/metadata/{key}")
	public MetaItemTemplate showMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@PathParam("key") String key, @Context ContainerRequestContext requestContext) throws Exception {
		return metadataController.show(imageId, key, requestContext);
	}

	@POST
	@Path("{imageId}/metadata")
	public MetadataTemplate createMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			MetadataTemplate metadata, @Context ContainerRequestContext requestContext) throws Exception {
		return metadataController.create(imageId, metadata, requestContext);
	}

	@PUT
	@Path("{imageId}/metadata/{key}")
	public MetaItemTemplate updateMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@PathParam("key") String key, MetaItemTemplate meta, @Context ContainerRequestContext requestContext)
			throws Exception {
		return metadataController.update(imageId, key, meta, requestContext);
	}

	@PUT
	@Path("{imageId}/metadata")
	public MetadataTemplate updateAllMetadata(@PathParam("projectId") String projectId,
			@PathParam("imageId") String imageId, MetadataTemplate metadata, @Context ContainerRequestContext requestContext)
			throws Exception {
		return metadataController.updataAll(imageId, metadata, requestContext);
	}

	@DELETE
	@Path("{imageId}/metadata/{key}")
	public Response deleteMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@PathParam("key") String key, @Context ContainerRequestContext requestContext) throws Exception {
		return metadataController.delete(imageId, key, requestContext);
	}

}
