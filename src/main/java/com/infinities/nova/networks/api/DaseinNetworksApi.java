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
package com.infinities.nova.networks.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.network.VlanCreateOptions;

import com.google.common.base.Preconditions;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.networks.model.Network;
import com.infinities.nova.networks.model.NetworkForCreateTemplate;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncVLANSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinNetworksApi implements NetworksApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinNetworksApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.api.NetworksApi#getNetworks(com.infinities
	 * .nova.NovaRequestContext, java.lang.String)
	 */
	@Override
	public List<Network> getNetworks(NovaRequestContext context, String projectId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Iterable<VLAN>> result = getSupport(context.getProjectId()).listVlans();
		Iterable<VLAN> iterable = result.get();
		Iterator<VLAN> iterator = iterable.iterator();

		List<Network> networks = new ArrayList<Network>();
		while (iterator.hasNext()) {
			VLAN vlan = iterator.next();
			networks.add(toNetwork(vlan));
		}

		return networks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.api.NetworksApi#getNetwork(com.infinities
	 * .nova.NovaRequestContext, java.lang.String, java.lang.String)
	 */
	@Override
	public Network getNetwork(NovaRequestContext context, String projectId, String networkId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<VLAN> result = getSupport(context.getProjectId()).getVlan(networkId);
		VLAN vlan = result.get();
		Network network = toNetwork(vlan);
		return network;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.api.NetworksApi#createNetwork(com.infinities
	 * .nova.NovaRequestContext, java.lang.String,
	 * com.infinities.nova.networks.model.NetworkForCreateTemplate)
	 */
	@Override
	public Network createNetwork(NovaRequestContext context, String projectId,
			NetworkForCreateTemplate networkForCreateTemplate) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		String name = networkForCreateTemplate.getNetwork().getLabel();
		String cidr = networkForCreateTemplate.getNetwork().getCidr();
		String[] dnsServers = { networkForCreateTemplate.getNetwork().getDhcpServer() };
		String[] ntpServers = {};
		String description = "";
		String domain = "";

		VlanCreateOptions options = VlanCreateOptions.getInstance(name, description, cidr, domain, dnsServers, ntpServers);
		AsyncResult<VLAN> result = getSupport(context.getProjectId()).createVlan(options);
		VLAN vlan = result.get();
		Network network = toNetwork(vlan);
		return network;
	}

	/**
	 * @param vlan
	 * @return
	 */
	private Network toNetwork(VLAN vlan) {
		Network network = new Network();
		network.setId(vlan.getProviderVlanId());
		network.setCidr(vlan.getCidr());
		network.setLabel(vlan.getName());
		return network;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.networks.api.NetworksApi#deleteNetwork(com.infinities
	 * .nova.NovaRequestContext, java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteNetwork(NovaRequestContext context, String projectId, String networkId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).removeVlan(networkId);
	}

	private AsyncVLANSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasNetworkServices()) {
			if (provider.getNetworkServices().hasVlanSupport()) {
				return provider.getNetworkServices().getVlanSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

}
