package com.infinities.nova.resource;

import java.util.ArrayList;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.wsgi.Resource;
import com.infinities.nova.model.wrapper.ExtensionWrapper;
import com.infinities.nova.response.model.Extension;
import com.infinities.nova.response.model.Extensions;
import com.infinities.skyport.util.FormatUtil;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExtensionsResource {

	private final static Extensions extensions = new Extensions();

	static {
		extensions.setList(new ArrayList<Extension>());
		Extension adminExtension = new Extension();
		adminExtension.setAlias("os-admin-actions");
		adminExtension
				.setDescription("Enable admin-only server actions\n\n    Actions include: pause, unpause, suspend, resume, migrate,\n    resetNetwork, injectNetworkInfo, lock, unlock, createBackup\n    ");
		adminExtension.setNamespace("http://docs.openstack.org/compute/ext/admin-actions/api/v1.1");
		adminExtension.setName("AdminActions");
		Calendar adminUpdated = Calendar.getInstance();
		adminUpdated.setTime(FormatUtil.getDateWithoutMiliSecond("2011-09-20T00:00:00Z"));
		adminExtension.setUpdated(adminUpdated);
		extensions.getList().add(adminExtension);

		Extension serverStartStopExtension = new Extension();
		serverStartStopExtension.setAlias("os-server-start-stop");
		serverStartStopExtension.setName("ServerStartStop");
		serverStartStopExtension.setDescription("Start/Stop instance compute API support.");
		serverStartStopExtension.setNamespace("http://docs.openstack.org/compute/ext/servers/api/v1.1");
		Calendar serverStartStopUpdated = Calendar.getInstance();
		serverStartStopUpdated.setTime(FormatUtil.getDateWithoutMiliSecond("2012-01-23T00:00:00Z"));
		serverStartStopExtension.setUpdated(serverStartStopUpdated);
		extensions.getList().add(serverStartStopExtension);
	}


	@GET
	public Extensions index(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return extensions;
	}

	@GET
	@Path("{alias}")
	public ExtensionWrapper show(@PathParam("projectId") String projectId, @PathParam("alias") String alias,
			@Context ContainerRequestContext requestContext) throws Exception {
		// NovaRequestContext novaContext = (NovaRequestContext)
		// requestContext.getProperty("nova.context");

		for (Extension extension : extensions) {
			if (alias.equals(extension.getAlias())) {
				return new ExtensionWrapper(extension);
			}
		}

		String msg = String.format("Extension %s could not be found.", alias);
		throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(msg).build());
	}

}
