package com.infinities.nova.api;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.api.middleware.Middleware;
import com.infinities.skyport.jpa.EntityManagerHelper;

@Priority(1001)
public class EntityManagerFilter extends Middleware {

	private static final Logger logger = LoggerFactory.getLogger(EntityManagerFilter.class);

	
	public EntityManagerFilter(){
		logger.debug("Register EntityManagerFilter");
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.debug("Start EntityManagerFilter");
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		try {
			EntityManagerHelper.commitAndClose();
		} catch (Exception e) {
			logger.error("commit request failed, please check the database", e);
		}
	}

}
