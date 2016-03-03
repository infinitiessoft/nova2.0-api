package com.infinities.neutron.subnets.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.network.AllocationPool;
import org.dasein.cloud.network.IPVersion;
import org.dasein.cloud.network.SubnetCreateOptions;
import org.dasein.cloud.network.VLAN;

import com.google.common.base.Preconditions;
import com.infinities.neutron.subnets.model.Pool;
import com.infinities.neutron.subnets.model.Subnet;
import com.infinities.neutron.subnets.model.Subnet.IpVersion;
import com.infinities.neutron.subnets.model.SubnetForCreateTemplate;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncVLANSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinSubnetsApi implements SubnetsApi{

	private ConfigurationHome configurationHome;
	
	@Inject
	public DaseinSubnetsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}
	
	@Override
	public List<Subnet> getSubnets(NovaRequestContext context, String projectId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		
		AsyncResult<Iterable<VLAN>> vlans = getSupport(context.getProjectId()).listVlans();
		Iterable<VLAN> vlanIterable = vlans.get();
		Iterator<VLAN> vlanIterator = vlanIterable.iterator();
		List<Subnet> subnets = new ArrayList<Subnet>();
		
		while (vlanIterator.hasNext()) {
			VLAN vlan = vlanIterator.next();
			AsyncResult<Iterable<org.dasein.cloud.network.Subnet>> result = getSupport(context.getProjectId()).listSubnets(vlan.getProviderVlanId());
			Iterable<org.dasein.cloud.network.Subnet> iterable = result.get();
			Iterator<org.dasein.cloud.network.Subnet> iterator = iterable.iterator();
			
			while(iterator.hasNext()) {
				org.dasein.cloud.network.Subnet subnet = iterator.next();
				subnets.add(toSubnet(subnet));
			}
		}
		return subnets;
	}
	
	@Override
	public Subnet getSubnet(NovaRequestContext context, String projectId, String subnetId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		
		AsyncResult<org.dasein.cloud.network.Subnet> result = getSupport(context.getProjectId()).getSubnet(subnetId);
		org.dasein.cloud.network.Subnet subnet = result.get();
		return toSubnet(subnet);
	}

	@Override
	public Subnet createSubnet(NovaRequestContext context, String projectId, SubnetForCreateTemplate subnetForCreateTemplate)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		String name = subnetForCreateTemplate.getSubnet().getName();
		String vlanId = subnetForCreateTemplate.getSubnet().getNetworkId();
		String cidr = subnetForCreateTemplate.getSubnet().getCidr();
		
		SubnetCreateOptions options = SubnetCreateOptions.getInstance(vlanId, cidr, name, null);
		IpVersion version = subnetForCreateTemplate.getSubnet().getIpversion();
		if (version.equals(IpVersion.IPV4)) {
			options.withSupportedTraffic(IPVersion.IPV4);
		} else if (version.equals(IpVersion.IPV6)) {
			options.withSupportedTraffic(IPVersion.IPV6);
		}
		AsyncResult<org.dasein.cloud.network.Subnet> result = getSupport(context.getProjectId()).createSubnet(options);
		org.dasein.cloud.network.Subnet subnet = result.get();
		return toSubnet(subnet);
	}

	@Override
	public Subnet updateSubnet(NovaRequestContext context, String projectId, String subnetId,
			SubnetForCreateTemplate subnetForCreateTemplate) throws Exception {
		throw new UnsupportedOperationException("subnet update not supported");
	}

	@Override
	public void deleteSubnet(NovaRequestContext context, String projectId, String subnetId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).removeSubnet(subnetId);
	}
	
	private Subnet toSubnet(org.dasein.cloud.network.Subnet subnet) {
		Subnet output = new Subnet();
		output.setId(subnet.getProviderSubnetId());
		output.setName(subnet.getName());
		output.setNetworkId(subnet.getProviderVlanId());
		output.setCidr(subnet.getCidr());
		output.setGw(subnet.getGateway().getIpAddress());
		IPVersion[] versions = subnet.getSupportedTraffic();
		for (IPVersion version : versions) {
			if (version.equals(IPVersion.IPV4)) {
				output.setIpversion(IpVersion.IPV4);
			} else if (version.equals(IPVersion.IPV6)) {
				output.setIpversion(IpVersion.IPV6);
			}
		}
		
		List<Pool> result = new ArrayList<Pool>();
		AllocationPool[] pools = subnet.getAllocationPools();
		for (AllocationPool pool : pools) {
			Pool tmp = new Pool();
			tmp.setStart(pool.getIpStart().getIpAddress());
			tmp.setEnd(pool.getIpEnd().getIpAddress());
			result.add(tmp);
		}
		output.setList(result);
		return output;
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
