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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infinities.api.openstack.commons.dynamicfeature.OpenstackContext;
import com.infinities.api.openstack.commons.namebinding.CheckProjectId;
import com.infinities.nova.networks.controller.NetworksController;
import com.infinities.nova.networks.model.NetworkForCreateTemplate;
import com.infinities.nova.networks.model.NetworkTemplate;
import com.infinities.nova.networks.model.Networks;

/**
 * @author pohsun
 *
 */
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
@OpenstackContext
public class NetworksResource {

	private final NetworksController controller;


	@Autowired
	public NetworksResource(NetworksController controller) {
		this.controller = controller;
	}

	@GET
	public Networks index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		return controller.index(requestContext, projectId);
	}

	@GET
	@Path("{networkId}")
	public NetworkTemplate show(@PathParam("projectId") String projectId, @PathParam("networkId") String networkId,
			@Context ContainerRequestContext requestContext) throws Exception {
		return controller.show(requestContext, projectId, networkId);
	}

	@POST
	public NetworkTemplate create(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext,
			NetworkForCreateTemplate networkForCreateTemplate) throws Exception {
		return controller.create(requestContext, projectId, networkForCreateTemplate);
	}

	@DELETE
	@Path("{networkId}")
	public Response detach(@PathParam("projectId") String projectId, @PathParam("networkId") String networkId,
			@Context ContainerRequestContext requestContext) throws Exception {
		controller.delete(projectId, networkId, requestContext);
		return Response.status(Status.ACCEPTED).build();
	}

}
