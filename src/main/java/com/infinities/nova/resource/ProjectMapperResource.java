package com.infinities.nova.resource;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectMapperResource {

	@Path("limits")
	public Class<LimitsResource> getLimitsResource() {
		return LimitsResource.class;
	}

	@Path("flavors")
	public Class<FlavorsResource> getFlavorsResource() {
		return FlavorsResource.class;
	}

	@Path("images")
	public Class<ImagesResource> getImagesResource() {
		return ImagesResource.class;
	}

	@Path("servers")
	public Class<ServersResource> getServersResource() {
		return ServersResource.class;
	}
}
