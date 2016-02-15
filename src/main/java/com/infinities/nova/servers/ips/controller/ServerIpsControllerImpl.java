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

import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.Common;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.Common.CommonNetwork;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.exception.http.HTTPNotFoundException;
import com.infinities.nova.response.model.Server.Addresses;
import com.infinities.nova.response.model.Server.Addresses.Address;
import com.infinities.nova.servers.api.ComputeApi;
import com.infinities.nova.views.address.ViewBuilder;

public class ServerIpsControllerImpl implements ServerIpsController {

	private final ComputeApi computeApi;
	private final ViewBuilder builder = new ViewBuilder();


	public ServerIpsControllerImpl(ComputeApi computeApi) {
		this.computeApi = computeApi;
	}

	private Instance getInstance(NovaRequestContext context, String serverId) throws Exception {
		Instance instance = computeApi.get(context, serverId, null);
		return instance;
	}

	@Override
	public Addresses index(ContainerRequestContext requestContext, String serverId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Instance instance = getInstance(context, serverId);
		Map<String, CommonNetwork> networks = Common.getNetworksForInstance(context, instance);
		return builder.index(networks);
	}

	@Override
	public Map<String, List<Address>> show(ContainerRequestContext requestContext, String serverId, String networkLabel)
			throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Instance instance = getInstance(context, serverId);
		Map<String, CommonNetwork> networks = Common.getNetworksForInstance(context, instance);

		if (!networks.containsKey(networkLabel)) {
			String msg = "Instance is not a member of specified network";
			throw new HTTPNotFoundException(msg);
		}

		return builder.show(networks.get(networkLabel), networkLabel);
	}

}
