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
package com.infinities.nova.api.openstack.compute.availablity_zones.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jersey.repackaged.com.google.common.collect.Maps;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.service.api.ServiceApi;
import com.infinities.nova.common.Config;
import com.infinities.nova.db.model.Service;

public class AvailabilityZoneApiImpl implements AvailabilityZoneApi {

	private final ServiceApi serviceApi;


	public AvailabilityZoneApiImpl(ServiceApi serviceApi) {
		this.serviceApi = serviceApi;
	}

	@Override
	public Entry<Map<String, Set<String>>, Map<String, Set<String>>> getAvailabilityZones(NovaRequestContext context,
			boolean getOnlyAvailable, boolean withHosts) {
		List<Service> enabledServices = serviceApi.getAll(context, false, false);
		enabledServices = setAvailabilityZones(context, enabledServices);

		Map<String, Set<String>> availableZones = new HashMap<String, Set<String>>();
		for (Service service : enabledServices) {
			String zone = service.getAvailiabilityZone();
			String host = service.getHost();

			if (!withHosts && !availableZones.containsKey(zone)) {
				availableZones.put(zone, new HashSet<String>());
			} else if (withHosts) {
				Set<String> zoneHosts = new HashSet<String>();
				zoneHosts.add(host);
				availableZones.put(zone, zoneHosts);
			}
		}

		Map<String, Set<String>> notAvailableZones = new HashMap<String, Set<String>>();
		if (!getOnlyAvailable) {
			List<Service> disabledServices = serviceApi.getAll(context, true, false);
			disabledServices = setAvailabilityZones(context, disabledServices);
			Map<String, String> zones = new HashMap<String, String>();
			for (Service service : disabledServices) {
				if (!availableZones.containsKey(service.getAvailiabilityZone())) {
					zones.put(service.getAvailiabilityZone(), service.getHost());
				}
			}

			for (Entry<String, String> entry : zones.entrySet()) {
				String zone = entry.getKey();
				String host = entry.getValue();
				if (!withHosts && !notAvailableZones.containsKey(zone)) {
					notAvailableZones.put(zone, new HashSet<String>());
				} else if (withHosts) {
					Set<String> zoneHosts = new HashSet<String>();
					zoneHosts.add(host);
					notAvailableZones.put(zone, zoneHosts);
				}
			}
		}
		return Maps.immutableEntry(availableZones, notAvailableZones);

	}

	public static List<Service> setAvailabilityZones(NovaRequestContext context, List<Service> services) {
		Set<String> hosts = new HashSet<String>();

		for (Service service : services) {
			hosts.add(service.getHost());
		}
		String az = Config.Instance.getOpt("default_availability_zone").asText();
		for (Service service : services) {
			service.setAvailiabilityZone(az);
		}

		return services;
	}
}
