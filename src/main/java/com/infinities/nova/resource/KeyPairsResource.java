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
import com.infinities.nova.keypairs.controller.KeyPairsController;
import com.infinities.nova.keypairs.model.KeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairsTemplate;

/**
 * @author pohsun
 *
 */
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
@OpenstackContext
public class KeyPairsResource {

	private final KeyPairsController controller;


	/**
	 * @param controller
	 */
	@Autowired
	public KeyPairsResource(KeyPairsController controller) {
		super();
		this.controller = controller;
	}

	@POST
	public MinimalKeyPairTemplate create(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext, KeyPairTemplate body) throws Exception {

		return controller.create(requestContext, body);
	}

	@GET
	public MinimalKeyPairsTemplate index(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.index(requestContext);
	}

	@GET
	@Path("{keyPairId}")
	public KeyPairTemplate show(@PathParam("projectId") String projectId, @PathParam("keyPairId") String keyPairId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.show(requestContext, keyPairId);
	}

	@DELETE
	@Path("{keyPairId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("keyPairId") String keyPairId,
			@Context ContainerRequestContext requestContext) throws Exception {

		controller.delete(keyPairId, requestContext);
		return Response.status(Status.ACCEPTED).build();
	}

}
