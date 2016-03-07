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
package com.infinities.nova.servers.ips.controller;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.db.model.Address;
import com.infinities.nova.db.model.Instance;

public class NetworkUtils {

	private NetworkUtils() {

	}

	// nova.api.openstack.common
	public static Map<String, Network> getNetworksForInstance(OpenstackRequestContext context, Instance instance)
			throws UnknownHostException, CloneNotSupportedException {
		List<Address> nwInfo = instance.getAddresses();
		return getNetworksForInstanceFromNwInfo(nwInfo);
	}

	public static Map<String, Network> getNetworksForInstanceFromNwInfo(List<Address> nwInfo) {
		Map<String, Network> networks = new HashMap<String, Network>();
		for (Address vif : nwInfo) {
			String label = vif.getNetwork();
			if (!networks.containsKey(label)) {
				networks.put(label, new Network());
			}
			networks.get(label).getIps().add(vif);
		}
		return networks;
	}


	public static class Network {

		private Set<Address> ips = new HashSet<Address>();
		private Set<Address> floatingIps = new HashSet<Address>();


		public Set<Address> getIps() {
			return ips;
		}

		public void setIps(Set<Address> ips) {
			this.ips = ips;
		}

		public Set<Address> getFloatingIps() {
			return floatingIps;
		}

		public void setFloatingIps(Set<Address> floatingIps) {
			this.floatingIps = floatingIps;
		}

	}
}
