package com.infinities.nova.api.middleware;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;

public abstract class Middleware implements ContainerRequestFilter, ContainerResponseFilter {

//	protected Response processRequest(Request request) {
//		return null;
//	}
//
//	protected Response processResponse(Response response) {
//		return response;
//	}
//
//	public Response process(Request request) {
//		Response response = processRequest(request);
//		if (response != null) {
//			return response;
//		}
//
//		return processResponse(response);
//	}

}
