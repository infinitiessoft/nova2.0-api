package com.infinities.neutron.resource;

import javax.inject.Inject;
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

import com.infinities.neutron.ports.controller.PortsController;
import com.infinities.neutron.ports.model.PortForCreateTemplate;
import com.infinities.neutron.ports.model.PortTemplate;
import com.infinities.neutron.ports.model.Ports;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.common.Resource;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PortsResource {

	private PortsController controller;
	
	@Inject
	public PortsResource(PortsController controller) {
		this.controller = controller;
	}
	
	@GET
	public Ports index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext, projectId);
	}
	
	@GET
	@Path("{portId}")
	public PortTemplate show(@PathParam("projectId") String projectId, @PathParam("portId") String portId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(requestContext, projectId, portId);
	}
	
	@POST
	public PortTemplate create(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext,
			PortForCreateTemplate portForCreateTemplate) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.create(requestContext, projectId, portForCreateTemplate);
	}
	
	@PUT
	@Path("{portId}")
	public PortTemplate update(@PathParam("projectId") String projectId, @PathParam("portId") String portId,
			@Context ContainerRequestContext requestContext, PortForCreateTemplate portForCreateTemplate) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.update(requestContext, projectId, portId, portForCreateTemplate);
	}

	@DELETE
	@Path("{portId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("portId") String portId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		controller.delete(projectId, portId, requestContext);
		return Response.status(Status.NO_CONTENT).build();
	}
}
