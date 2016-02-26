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
package com.infinities.nova.networks.views;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.networks.model.Network;
import com.infinities.nova.networks.model.NetworkTemplate;
import com.infinities.nova.networks.model.Networks;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param networks
	 * @return
	 */
	public Networks index(ContainerRequestContext requestContext, List<Network> networks) {
		Networks ret = new Networks();
		ret.setList(networks);
		return ret;
	}

	/**
	 * @param requestContext
	 * @param network
	 * @return
	 */
	public NetworkTemplate show(ContainerRequestContext requestContext, Network network) {
		NetworkTemplate template = new NetworkTemplate();
		template.setNetwork(network);
		return template;
	}

}
