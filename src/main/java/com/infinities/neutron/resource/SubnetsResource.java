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

import com.infinities.neutron.subnets.controller.SubnetsController;
import com.infinities.neutron.subnets.model.SubnetForCreateTemplate;
import com.infinities.neutron.subnets.model.SubnetTemplate;
import com.infinities.neutron.subnets.model.Subnets;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.common.Resource;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubnetsResource {

	private final SubnetsController controller;
	
	@Inject
	public SubnetsResource(SubnetsController controller) {
		this.controller = controller;
	}
	
	@GET
	public Subnets index(@PathParam("projectId") String projectId,@Context ContainerRequestContext requestContext) throws Exception {
		System.err.println("************* List Subnets Resource");
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext, projectId);
	}
	
	@GET
	@Path("{subnetId}")
	public SubnetTemplate show(@PathParam("projectId") String projectId, @PathParam("subnetId") String subnetId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(requestContext, projectId, subnetId);
	}
	
	@POST
	public SubnetTemplate create(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext,
			SubnetForCreateTemplate subnet) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.create(requestContext, projectId, subnet);
	}
	
	@PUT
	@Path("{subnetId}")
	public SubnetTemplate update(@PathParam("projectId") String projectId, @PathParam("subnetId") String subnetId,
			@Context ContainerRequestContext requestContext, SubnetForCreateTemplate subnet) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.update(requestContext, projectId, subnetId, subnet);
	}

	@DELETE
	@Path("{subnetId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("subnetId") String subnetId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		controller.delete(projectId, subnetId, requestContext);
		return Response.status(Status.NO_CONTENT).build();
	}
}
