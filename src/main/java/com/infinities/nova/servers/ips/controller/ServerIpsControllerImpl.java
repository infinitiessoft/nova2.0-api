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

import com.infinities.api.openstack.commons.config.Config;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.exception.http.HTTPNotFoundException;
import com.infinities.nova.servers.api.ComputeApi;
import com.infinities.nova.servers.ips.views.ViewBuilder;
import com.infinities.nova.servers.model.Server;
import com.infinities.nova.servers.model.Server.Addresses;
import com.infinities.nova.servers.model.Server.Addresses.Address;

public class ServerIpsControllerImpl implements ServerIpsController {

	private final ComputeApi computeApi;
	private final ViewBuilder builder;


	public ServerIpsControllerImpl(Config config, ComputeApi computeApi) {
		this.computeApi = computeApi;
		String osapiComputeLinkPrefix = config.getOpt("osapi_compute_link_prefix").asText();
		builder = new ViewBuilder(osapiComputeLinkPrefix);
	}

	private Server getInstance(OpenstackRequestContext context, String serverId) throws Exception {
		Server instance = computeApi.get(context, serverId, null);
		return instance;
	}

	@Override
	public Addresses index(ContainerRequestContext requestContext, String serverId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getInstance(context, serverId);
		return builder.index(instance.getAddresses());
	}

	@Override
	public Map<String, List<Address>> show(ContainerRequestContext requestContext, String serverId, String networkLabel)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getInstance(context, serverId);
		Addresses addresses = instance.getAddresses();
		if (!addresses.getAddresses().containsKey(networkLabel)) {
			String msg = "Instance is not a member of specified network";
			throw new HTTPNotFoundException(msg);
		}

		return builder.show(addresses.getAddresses().get(networkLabel), networkLabel);
	}

}
