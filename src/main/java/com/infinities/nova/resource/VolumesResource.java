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

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.common.Resource;
import com.infinities.nova.volumes.controller.VolumesController;
import com.infinities.nova.volumes.model.VolumeForCreateTemplate;
import com.infinities.nova.volumes.model.VolumeTemplate;
import com.infinities.nova.volumes.model.Volumes;

/**
 * @author pohsun
 *
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VolumesResource {

	private VolumesController controller;


	@Inject
	public VolumesResource(VolumesController controller) {
		this.controller = controller;
	}

	@GET
	public Volumes index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext, projectId);
	}

	@GET
	@Path("detail")
	public Volumes detail(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext, projectId);
	}

	@GET
	@Path("{volumeId}")
	public VolumeTemplate show(@PathParam("projectId") String projectId, @PathParam("volumeId") String volumeId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(requestContext, projectId, volumeId);
	}

	@POST
	public VolumeTemplate create(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext,
			VolumeForCreateTemplate volumeForCreateTemplate) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.create(requestContext, projectId, volumeForCreateTemplate);
	}

	@DELETE
	@Path("{volumeId}")
	public Response detach(@PathParam("projectId") String projectId, @PathParam("volumeId") String volumeId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		controller.delete(projectId, volumeId, requestContext);
		return Response.status(Status.ACCEPTED).build();
	}

}
