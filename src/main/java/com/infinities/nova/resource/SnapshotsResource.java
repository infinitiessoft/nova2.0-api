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

import org.springframework.stereotype.Component;

import com.infinities.nova.namebinding.CheckProjectId;
import com.infinities.nova.snapshots.controller.SnapshotsController;
import com.infinities.nova.snapshots.model.SnapshotForCreateTemplate;
import com.infinities.nova.snapshots.model.SnapshotTemplate;
import com.infinities.nova.snapshots.model.Snapshots;

/**
 * @author pohsun
 *
 */
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
public class SnapshotsResource {

	private SnapshotsController controller;


	@Inject
	public SnapshotsResource(SnapshotsController controller) {
		this.controller = controller;
	}

	@GET
	public Snapshots index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {

		return controller.index(requestContext, projectId);
	}

	@GET
	@Path("detail")
	public Snapshots detail(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {

		return controller.index(requestContext, projectId);
	}

	@GET
	@Path("{snapshotId}")
	public SnapshotTemplate show(@PathParam("projectId") String projectId, @PathParam("snapshotId") String snapshotId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.show(requestContext, projectId, snapshotId);
	}

	@POST
	public SnapshotTemplate create(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext, SnapshotForCreateTemplate snapshotForCreateTemplate)
			throws Exception {

		return controller.create(requestContext, projectId, snapshotForCreateTemplate);
	}

	@DELETE
	@Path("{snapshotId}")
	public Response detach(@PathParam("projectId") String projectId, @PathParam("snapshotId") String snapshotId,
			@Context ContainerRequestContext requestContext) throws Exception {

		controller.delete(projectId, snapshotId, requestContext);
		return Response.status(Status.ACCEPTED).build();
	}

}
