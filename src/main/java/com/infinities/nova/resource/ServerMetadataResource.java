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
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infinities.api.openstack.commons.dynamicfeature.OpenstackContext;
import com.infinities.api.openstack.commons.namebinding.CheckProjectId;
import com.infinities.nova.common.model.MetaItemTemplate;
import com.infinities.nova.common.model.MetadataTemplate;
import com.infinities.nova.servers.metadata.controller.ServerMetadataController;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
@OpenstackContext
public class ServerMetadataResource {

	private final ServerMetadataController controller;


	@Autowired
	public ServerMetadataResource(ServerMetadataController controller) {
		this.controller = controller;
	}

	@GET
	public MetadataTemplate index(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.index(requestContext, serverId);
	}

	@POST
	public MetadataTemplate create(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			MetadataTemplate metadata, @Context ContainerRequestContext requestContext) throws Exception {

		return controller.create(requestContext, serverId, metadata);
	}

	@PUT
	@Path("{key}")
	public MetaItemTemplate update(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@PathParam("key") String key, MetaItemTemplate meta, @Context ContainerRequestContext requestContext)
			throws Exception {

		return controller.update(requestContext, serverId, key, meta);
	}

	@PUT
	public MetadataTemplate updateAll(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			MetadataTemplate metadata, @Context ContainerRequestContext requestContext) throws Exception {

		return controller.updateAll(requestContext, serverId, metadata);
	}

	@GET
	@Path("{key}")
	public MetaItemTemplate show(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@PathParam("key") String key, @Context ContainerRequestContext requestContext) throws Exception {

		return controller.show(requestContext, serverId, key);
	}

	@DELETE
	@Path("{key}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@PathParam("key") String key, @Context ContainerRequestContext requestContext) throws Exception {

		controller.delete(requestContext, serverId, key);
		return Response.status(Status.NO_CONTENT).build();
	}

}
