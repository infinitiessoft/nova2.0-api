package com.infinities.nova.api.middleware;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import com.infinities.nova.openstack.common.Context;

@Priority(1002)
public class ComputeReqIdMiddleware extends Middleware {

	public final static String ENV_REQUEST_ID = "openstack.request_id";
	private final static String HTTP_RESP_HEADER_REQUEST_ID = "x-compute-request-id";


	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String reqId = Context.generateRequestId();
		requestContext.setProperty(ENV_REQUEST_ID, reqId);
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if (!responseContext.getHeaders().containsKey(HTTP_RESP_HEADER_REQUEST_ID)) {
			responseContext.getHeaders().add(HTTP_RESP_HEADER_REQUEST_ID, requestContext.getProperty(ENV_REQUEST_ID));
		}
	}

}
