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
import com.infinities.nova.api.openstack.wsgi.Resource;
import com.infinities.nova.keypairs.controller.KeyPairsController;
import com.infinities.nova.keypairs.model.KeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairsTemplate;

/**
 * @author pohsun
 *
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KeyPairsResource {

	private final KeyPairsController controller;


	@Inject
	public KeyPairsResource(KeyPairsController controller) {
		this.controller = controller;
	}

	@POST
	public MinimalKeyPairTemplate create(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext, KeyPairTemplate body) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.create(requestContext, body);
	}

	@GET
	public MinimalKeyPairsTemplate index(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext);
	}

	@GET
	@Path("{keyPairId}")
	public KeyPairTemplate show(@PathParam("projectId") String projectId, @PathParam("keyPairId") String keyPairId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(requestContext, keyPairId);
	}

	@DELETE
	@Path("{keyPairId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("keyPairId") String keyPairId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		controller.delete(keyPairId, requestContext);
		return Response.status(Status.NO_CONTENT).build();
	}

}
