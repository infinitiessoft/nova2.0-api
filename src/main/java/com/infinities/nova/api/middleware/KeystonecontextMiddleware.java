package com.infinities.nova.api.middleware;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(1005)
public class KeystonecontextMiddleware extends Middleware {

	private final static Logger logger = LoggerFactory.getLogger(KeystonecontextMiddleware.class);
	@Context HttpServletRequest httpRequest;


	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.debug("KeystonecontextMiddleware begin");
		NovaKeystoneContextMiddleware middleware = new NovaKeystoneContextMiddleware();
		middleware.setRequest(httpRequest);
		try {
			middleware.call(requestContext);
		} catch (Exception e) {
			logger.error("keystoneContext problem",e);
			throw new RuntimeException(e);
		}
		logger.debug("KeystonecontextMiddleware end");
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

	}

}
