package com.infinities.neutron.networks.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.network.VLANState;
import org.dasein.cloud.network.VlanCreateOptions;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.common.base.Preconditions;
import com.infinities.neutron.networks.model.Network;
import com.infinities.neutron.networks.model.NetworkForCreate;
import com.infinities.neutron.networks.model.NetworkForUpdate;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncVLANSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinNetworksApi implements NetworksApi{
	
	private static final String NETWORK_TYPE = "vlan";
	private ConfigurationHome configurationHome;


	@Inject
	public DaseinNetworksApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

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
	
	private Network toNetwork(VLAN vlan) throws CloudException, InternalException, JSONException {
		Network network = new Network();
		network.setId(vlan.getProviderVlanId());
		network.setName(vlan.getName());
		network.setStatus(toStatus(vlan.getCurrentState()));
		network.setProviderNetworkType(NETWORK_TYPE);
		network.setTenantId(vlan.getTag("tenant_id"));
		JSONArray jsonArray = new JSONArray(vlan.getTag("subnets"));
		List<String> subnets = new ArrayList<String>();
		for (int i = 0 ; i < jsonArray.length() ; i++) {
			subnets.add(jsonArray.getString(i));
		}
		network.setSubnets(subnets);
		return network;
	}
	
	private String toStatus(VLANState state) {
		if (state.equals(VLANState.AVAILABLE)) {
			return "ACTIVE";
		}
		return "BUILD";
	}
	
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

	@Override
	public Network createNetwork(NovaRequestContext context, String projectId, NetworkForCreate networkForCreate)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		String name = networkForCreate.getName();
		
		VlanCreateOptions options = VlanCreateOptions.getInstance(name, null, null, null, null, null);
		AsyncResult<VLAN> result = getSupport(context.getProjectId()).createVlan(options);
		VLAN vlan = result.get();
		Network network = toNetwork(vlan);
		return network;
	}

	@Override
	public Network updateNetwork(NovaRequestContext context, String projectId, String networkId,
			NetworkForUpdate networkForUpdate) throws Exception {
		throw new UnsupportedOperationException("network update not supported");
	}
	
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
