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
