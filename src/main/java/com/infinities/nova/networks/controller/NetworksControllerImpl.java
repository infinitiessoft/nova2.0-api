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
package com.infinities.nova.networks.controller;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.networks.api.NetworksApi;
import com.infinities.nova.networks.model.Network;
import com.infinities.nova.networks.model.NetworkForCreateTemplate;
import com.infinities.nova.networks.model.NetworkTemplate;
import com.infinities.nova.networks.model.Networks;
import com.infinities.nova.networks.views.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class NetworksControllerImpl implements NetworksController {

	private final NetworksApi networksApi;
	private final ViewBuilder builder = new ViewBuilder();


	public NetworksControllerImpl(NetworksApi networksApi) {
		this.networksApi = networksApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.controller.NetworksController#index(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String)
	 */
	@Override
	public Networks index(ContainerRequestContext requestContext, String projectId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		List<Network> networks = networksApi.getNetworks(context, projectId);
		return builder.index(requestContext, networks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.controller.NetworksController#show(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public NetworkTemplate show(ContainerRequestContext requestContext, String projectId, String networkId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Network network = networksApi.getNetwork(context, projectId, networkId);

		return builder.show(requestContext, network);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.controller.NetworksController#create(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String,
	 * com.infinities.nova.networks.model.NetworkForCreateTemplate)
	 */
	@Override
	public NetworkTemplate create(ContainerRequestContext requestContext, String projectId,
			NetworkForCreateTemplate networkForCreateTemplate) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Network network = networksApi.createNetwork(context, projectId, networkForCreateTemplate);

		return builder.show(requestContext, network);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.controller.NetworksController#delete(java
	 * .lang.String, java.lang.String,
	 * javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void delete(String projectId, String networkId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		networksApi.deleteNetwork(context, projectId, networkId);
	}

}
