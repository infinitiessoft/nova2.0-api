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

import com.infinities.neutron.networks.controller.NetworksController;
import com.infinities.neutron.networks.model.NetworkForCreate;
import com.infinities.neutron.networks.model.NetworkForUpdate;
import com.infinities.neutron.networks.model.NetworkTemplate;
import com.infinities.neutron.networks.model.Networks;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.common.Resource;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NetworksResource {

	private NetworksController controller;
	
	@Inject
	public NetworksResource(NetworksController controller) {
		this.controller = controller;
	}
	
	@GET
	public Networks index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext, projectId);
	}
	
	@GET
	@Path("{networkId}")
	public NetworkTemplate show(@PathParam("projectId") String projectId, @PathParam("networkId") String networkId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(requestContext, projectId, networkId);
	}
	
	@POST
	public NetworkTemplate create(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext,
			NetworkForCreate networkForCreate) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.create(requestContext, projectId, networkForCreate);
	}
	
	@PUT
	@Path("{networkId}")
	public NetworkTemplate update(@PathParam("projectId") String projectId, @PathParam("networkId") String networkId, @Context ContainerRequestContext requestContext,
			NetworkForUpdate networkForUpdate) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.update(requestContext, projectId, networkId, networkForUpdate);
	}
	
	@DELETE
	@Path("{networkId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("networkId") String networkId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		controller.delete(projectId, networkId, requestContext);
		return Response.status(Status.ACCEPTED).build();
	}
}
