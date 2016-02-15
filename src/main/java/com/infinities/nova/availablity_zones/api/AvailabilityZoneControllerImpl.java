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
package com.infinities.nova.availablity_zones.api;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * @author pohsun
 *
 */
public class AvailabilityZoneControllerImpl implements AvailabilityZoneController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.api.openstack.compute.availablity_zones.api.
	 * AvailabilityZoneController
	 * #index(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public AvailabilityZonesTemplate index(ContainerRequestContext requestContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.api.openstack.compute.availablity_zones.api.
	 * AvailabilityZoneController
	 * #detail(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public AvailabilityZonesTemplate detail(ContainerRequestContext requestContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
