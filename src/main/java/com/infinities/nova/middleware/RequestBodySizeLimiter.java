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
package com.infinities.nova.middleware;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Response;

import com.infinities.nova.common.config.Config;

@Priority(1003)
public class RequestBodySizeLimiter extends Middleware {

	private final int maxRequestSize;


	public RequestBodySizeLimiter() {
		maxRequestSize = Config.Instance.getOpt("osapi_max_request_body_size").asInteger();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getLength() > maxRequestSize) {
			requestContext.abortWith(Response.status(Response.Status.REQUEST_ENTITY_TOO_LARGE)
					.entity("Request is too large.").build());
		}

		if (requestContext.getEntityStream().available() > maxRequestSize) {
			requestContext.abortWith(Response.status(Response.Status.REQUEST_ENTITY_TOO_LARGE)
					.entity("Request is too large.").build());
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

	}

}
