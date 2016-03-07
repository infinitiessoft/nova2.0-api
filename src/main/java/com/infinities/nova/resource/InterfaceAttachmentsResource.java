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

import com.infinities.api.openstack.commons.namebinding.CheckProjectId;
import com.infinities.nova.servers.interfaces.controller.InterfaceAttachmentsController;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentForCreateTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachments;

/**
 * @author pohsun
 *
 */
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
public class InterfaceAttachmentsResource {

	private final InterfaceAttachmentsController controller;


	/**
	 * @param controller
	 */
	@Autowired
	public InterfaceAttachmentsResource(InterfaceAttachmentsController controller) {
		super();
		this.controller = controller;
	}

	@GET
	public InterfaceAttachments index(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.index(requestContext, projectId, serverId);
	}

	@GET
	@Path("{interfaceAttachmentId}")
	public InterfaceAttachmentTemplate show(@PathParam("projectId") String projectId,
			@PathParam("serverId") String serverId, @PathParam("interfaceAttachmentId") String interfaceAttachmentId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.show(requestContext, projectId, serverId, interfaceAttachmentId);
	}

	@POST
	public InterfaceAttachmentTemplate create(@PathParam("projectId") String projectId,
			@PathParam("serverId") String serverId, @Context ContainerRequestContext requestContext,
			InterfaceAttachmentForCreateTemplate interfaceAttachmentForCreateTemplate) throws Exception {

		return controller.attach(requestContext, projectId, serverId, interfaceAttachmentForCreateTemplate);
	}

	@DELETE
	@Path("{interfaceAttachmentId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@PathParam("interfaceAttachmentId") String interfaceAttachmentId, @Context ContainerRequestContext requestContext)
			throws Exception {

		controller.detach(projectId, serverId, interfaceAttachmentId, requestContext);
		return Response.status(Status.ACCEPTED).build();
	}

}
