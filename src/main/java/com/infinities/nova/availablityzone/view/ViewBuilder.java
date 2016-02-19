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
package com.infinities.nova.availablityzone.view;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.availablityzone.model.AvailabilityZone;
import com.infinities.nova.availablityzone.model.AvailabilityZoneTemplate;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param zones
	 * @return
	 */
	public AvailabilityZoneTemplate index(ContainerRequestContext requestContext, List<AvailabilityZone> zones) {
		for (AvailabilityZone zone : zones) {
			zone.setHosts(null);
		}
		AvailabilityZoneTemplate t = new AvailabilityZoneTemplate(zones);
		return t;
	}

	/**
	 * @param requestContext
	 * @param zones
	 * @return
	 */
	public AvailabilityZoneTemplate detail(ContainerRequestContext requestContext, List<AvailabilityZone> zones) {
		AvailabilityZoneTemplate t = new AvailabilityZoneTemplate(zones);
		return t;
	}

}
