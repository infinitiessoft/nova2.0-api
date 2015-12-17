package com.infinities.nova.resource;

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

import com.infinities.nova.api.VersionsApi;

// [composite:osapi_compute]
// use = call:nova.api.openstack.urlmap:urlmap_factory
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class NovaResource {

	private final VersionsApi versionsApi;


	@Inject
	public NovaResource(VersionsApi versionsApi) {
		this.versionsApi = versionsApi;
	}

	// /v2: openstack_compute_api_v2
	@Path("/v2")
	public Class<Version2Resource> getApiV2Resource() {
		return Version2Resource.class;
	}

	// /v3: openstack_compute_api_v3
	// @Path("/v3")
	// public Class<Version3Resource> getApiV3Resource() {
	// return Version3Resource.class;
	// }

	// /: oscomputeversions
	@GET
	public Response index(@Context UriInfo uri) throws URISyntaxException {
		if (uri.getRequestUri().toString().endsWith("/")) {
			return versionsApi.index(uri.getRequestUri());
		} else {
			return versionsApi.multi(uri.getRequestUri(), uri.getPath());
		}
	}
	// @Path("/")
	// @GET
	// public Response multi(@PathParam("version") String version, @Context
	// UriInfo uri) throws URISyntaxException {
	// return versionsApi.multi(uri.getRequestUri().toString(), uri.getPath());
	// }

}
