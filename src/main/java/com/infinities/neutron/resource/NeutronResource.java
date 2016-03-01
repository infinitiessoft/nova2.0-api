package com.infinities.neutron.resource;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.infinities.nova.resource.Version2Resource;
import com.infinities.nova.versions.api.VersionsApi;

@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class NeutronResource {

	private final VersionsApi versionsApi;


	@Inject
	public NeutronResource(VersionsApi versionsApi) {
		this.versionsApi = versionsApi;
	}

	@Path("/v2")
	public Class<Version2Resource> getApiV2Resource() {
		return Version2Resource.class;
	}

	@GET
	public Response index(@Context UriInfo uri) throws URISyntaxException {
		if (uri.getRequestUri().toString().endsWith("/")) {
			return versionsApi.index(uri.getRequestUri());
		} else {
			return versionsApi.multi(uri.getRequestUri(), uri.getPath());
		}
	}
}
