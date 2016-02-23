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
package com.infinities.nova.availablityzone.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.dc.DataCenter;

import com.google.common.base.Preconditions;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.availablityzone.model.AvailabilityZone;
import com.infinities.nova.availablityzone.model.Service;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.AsyncDataCenterServices;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinAvailabilityZoneApi implements AvailabilityZoneApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinAvailabilityZoneApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	@Override
	public List<AvailabilityZone> getAvailabilityZones(NovaRequestContext context) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Iterable<DataCenter>> result =
				getServices(context.getProjectId()).listDataCenters(getRegionId(context.getProjectId()));
		Iterable<DataCenter> dcs = result.get();
		List<AvailabilityZone> zones = new ArrayList<AvailabilityZone>();
		for (DataCenter dc : dcs) {
			zones.add(toAvailabilityZone(dc));
		}
		return zones;
	}

	/**
	 * @param dc
	 * @return
	 */
	private AvailabilityZone toAvailabilityZone(DataCenter dc) {
		AvailabilityZone zone = new AvailabilityZone();
		zone.setZoneName(dc.getName());
		AvailabilityZone.ZoneState state = new AvailabilityZone.ZoneState();
		state.setAvailable(dc.isActive());
		zone.setZoneState(state);
		zone.setHosts(new HashMap<String, Map<String, Service>>());
		return zone;
	}

	private AsyncDataCenterServices getServices(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);
		return provider.getDataCenterServices();

	}

	private String getRegionId(String id) {
		CachedServiceProvider provider = configurationHome.findById(id);
		Preconditions.checkArgument(provider != null, "invalid project id:" + id);
		return provider.getContext().getRegionId();
	}
}
