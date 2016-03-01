package com.infinities.neutron.ports.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.network.NICCreateOptions;
import org.dasein.cloud.network.NICState;
import org.dasein.cloud.network.NetworkInterface;
import org.dasein.cloud.network.RawAddress;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.infinities.neutron.ports.model.Port;
import com.infinities.neutron.ports.model.Port.Binding;
import com.infinities.neutron.ports.model.Port.Ip;
import com.infinities.neutron.ports.model.PortForCreateTemplate;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncVLANSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinPortsApi implements PortsApi{

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinPortsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	@Override
	public List<Port> getPorts(NovaRequestContext context, String projectId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Iterable<NetworkInterface>> result = getSupport(context.getProjectId()).listNetworkInterfaces();
		Iterable<NetworkInterface> iterable = result.get();
		Iterator<NetworkInterface> iterator = iterable.iterator();
		 
		List<Port> ports = new ArrayList<Port>();
		while(iterator.hasNext()) {
			NetworkInterface nic = iterator.next();
			ports.add(toPort(nic));
		}
		return ports;
	}
	
	@Override
	public Port getPort(NovaRequestContext context, String projectId, String portId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<NetworkInterface> result = getSupport(context.getProjectId()).getNetworkInterface(portId);
		NetworkInterface nic = result.get();
		 
		return toPort(nic);
	}
	
	@Override
	public Port createPort(NovaRequestContext context, String projectId, PortForCreateTemplate portForCreateTemplate) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		String vlanId = portForCreateTemplate.getPort().getNetworkId();
		String name = portForCreateTemplate.getPort().getName();
		
		NICCreateOptions options = NICCreateOptions.getInstanceForVlan(vlanId, name, null);
		AsyncResult<NetworkInterface> result = getSupport(context.getProjectId()).createNetworkInterface(options);
		NetworkInterface nic = result.get();
		 
		return toPort(nic);
	}
	

	@Override
	public Port updatePort(NovaRequestContext context, String projectId, String portId,
			PortForCreateTemplate portForCreateTemplate) throws Exception {
		throw new UnsupportedOperationException("port update not supported");
	}

	@Override
	public void deletePort(NovaRequestContext context, String projectId, String portId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).removeNetworkInterface(portId);
	}
	
	private Port toPort(NetworkInterface nic) {
		Port output = new Port();
		output.setId(nic.getProviderNetworkInterfaceId());
		output.setTenantId(nic.getProviderOwnerId());
		output.setName(nic.getName());
		output.setMacAddress(nic.getMacAddress());
		output.setNetworkId(nic.getProviderVlanId());
		if (!Strings.isNullOrEmpty(nic.getProviderSubnetId())) {
			List<Ip> ips = new ArrayList<Ip>();
			RawAddress[] addresses = nic.getIpAddresses();
			for (int i = 0 ; i < addresses.length ; i++) {
				Ip ip = new Ip();
				ip.setSubnetId(nic.getProviderSubnetId());
				ip.setAddress(nic.getIpAddresses()[i].getIpAddress());
				ips.add(ip);
			}
			output.setList(ips);
		}
		output.setStatus(toState(nic.getCurrentState()));
		Map<String, String> tags = nic.getTags();
		Set<String> keys = tags.keySet();
		for (String key : keys) {
			if (key.equals("binding:host_id")) {
				Binding binding = new Binding();
				binding.setHostId(tags.get("binding:host_id"));
				binding.setVifType(tags.get("binding:vif_type"));
				binding.setVnicType(tags.get("binding:vnic_type"));
				output.setBinding(binding);
			}
			if (key.equals("device_owner")) {
				output.setDeviceOwner(tags.get("device_owner"));
			}
			if (key.equals("device_id")) {
				output.setDeviceId(tags.get("device_id"));
			}
		}
		return output;
	}
	
	private String toState(NICState state) {
		if (state.equals(NICState.IN_USE)) {
			return "ACTIVE";
		}
		return "DOWN";
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
