package com.infinities.nova.api.middleware;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import com.infinities.keystonemiddleware.AuthProtocol;

@Priority(1004)
public class AuthTokenMiddleware extends Middleware {

	private final AuthProtocol authProtocol;


	public AuthTokenMiddleware() throws MalformedURLException {
		authProtocol = new AuthProtocol();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		authProtocol.call(requestContext);
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

	}

}
