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
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.infinities.nova.versions.v2.api.Version2Api;

@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Version2Resource {

	private final Version2Api versionApi;


	@Inject
	public Version2Resource(Version2Api versionApi) {
		this.versionApi = versionApi;
	}

	@GET
	public Response getVersion(@Context UriInfo uri) {
		return versionApi.show(uri.getRequestUri());
	}

	@Path("{projectId}")
	public Class<ProjectMapperResource> getProjectMapperResource(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext) {
		// nova.wsgi.Resource.call
		return ProjectMapperResource.class;
	}

	@Path("extensions")
	public Class<ExtensionsResource> getExtensionsResource() {
		return ExtensionsResource.class;
	}
}
