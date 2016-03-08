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
package com.infinities.nova.servers.ips.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infinities.api.openstack.commons.views.AbstractViewBuilder;
import com.infinities.nova.servers.model.Server.Addresses;
import com.infinities.nova.servers.model.Server.Addresses.Address;

public class ViewBuilder extends AbstractViewBuilder {

	// private final static String COLLECTION_NAME = "addresses";

	/**
	 * @param config
	 */
	public ViewBuilder(String osapiComputeLinkPrefix) {
		super(osapiComputeLinkPrefix);
	}

	public Addresses index(Addresses addressess) {
		return addressess;
	}

	public Map<String, List<Address>> show(List<Address> addresses, String label) {
		Map<String, List<Address>> map = new HashMap<String, List<Address>>();
		map.put(label, addresses);
		return map;
	}
}
