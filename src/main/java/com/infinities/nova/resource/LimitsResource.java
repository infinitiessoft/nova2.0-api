package com.infinities.nova.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.limits.LimitsController;
import com.infinities.nova.api.openstack.compute.limits.LimitsTemplate;
import com.infinities.nova.api.openstack.wsgi.Resource;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LimitsResource {

	private final LimitsController controller;


	@Inject
	public LimitsResource(LimitsController controller) {
		this.controller = controller;
	}

	@GET
	public LimitsTemplate index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext);
	}

}
