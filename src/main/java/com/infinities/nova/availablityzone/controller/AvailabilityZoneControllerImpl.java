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
package com.infinities.nova.availablityzone.controller;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.availablityzone.api.AvailabilityZoneApi;
import com.infinities.nova.availablityzone.model.AvailabilityZone;
import com.infinities.nova.availablityzone.model.AvailabilityZoneTemplate;
import com.infinities.nova.availablityzone.view.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class AvailabilityZoneControllerImpl implements AvailabilityZoneController {

	// private final static Logger logger =
	// LoggerFactory.getLogger(AvailabilityZoneControllerImpl.class);

	private final ViewBuilder builder = new ViewBuilder();

	private final AvailabilityZoneApi availabilityZoneApi;


	public AvailabilityZoneControllerImpl(AvailabilityZoneApi availabilityZoneApi) {
		this.availabilityZoneApi = availabilityZoneApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.availablityzone.controller.AvailabilityZoneController
	 * #index(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public AvailabilityZoneTemplate index(ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<AvailabilityZone> zones = availabilityZoneApi.getAvailabilityZones(context);
		return builder.index(requestContext, zones);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.availablityzone.controller.AvailabilityZoneController
	 * #detail(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public AvailabilityZoneTemplate detail(ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<AvailabilityZone> zones = availabilityZoneApi.getAvailabilityZones(context);
		return builder.detail(requestContext, zones);
	}

}
