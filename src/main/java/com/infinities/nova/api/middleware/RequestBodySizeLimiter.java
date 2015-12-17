package com.infinities.nova.api.middleware;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Response;

import com.infinities.nova.common.Config;

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
