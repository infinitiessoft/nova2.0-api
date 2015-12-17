package com.infinities.nova.resource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.servers.ips.ServerIpsController;
import com.infinities.nova.api.openstack.wsgi.Resource;
import com.infinities.nova.response.model.Server.Addresses;
import com.infinities.nova.response.model.Server.Addresses.Address;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServerIpsResource {

	private final ServerIpsController controller;


	@Inject
	public ServerIpsResource(ServerIpsController controller) {
		this.controller = controller;
	}

	@GET
	public Addresses index(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext, serverId);
	}

	@GET
	@Path("{networkLabel}")
	public Map<String, List<Address>> show(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@PathParam("networkLabel") String networkLabel, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(requestContext, serverId, networkLabel);
	}
}
