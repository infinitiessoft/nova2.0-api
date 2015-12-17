package com.infinities.nova.api.openstack.compute.servers.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Tag;
import org.dasein.cloud.compute.VMLaunchOptions;
import org.dasein.cloud.network.Firewall;

import com.google.common.base.Preconditions;
import com.infinities.nova.api.Context;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.task.ComputeTaskApi;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.response.model.NetworkForCreate;
import com.infinities.skyport.async.service.network.AsyncFirewallSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedVirtualMachineSupport;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinComputeTaskApi implements ComputeTaskApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinComputeTaskApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	@Override
	public List<Instance> buildInstances(NovaRequestContext context, CreateVmBaseOptions options, int num, Image bootMeta,
			String adminPassword, List<Entry<String, String>> injectedFiles, List<NetworkForCreate> requestedNetworks,
			List<String> securityGroups) throws CloudException, InternalException, ConcurrentException,
			InterruptedException, ExecutionException {
		VMLaunchOptions withLaunchOptions =
				VMLaunchOptions.getInstance(options.getInstanceTypeId(), bootMeta.getId(), options.getDisplayName(),
						options.getDisplayDescription());
		String[] firewallIds = new String[securityGroups.size()];
		try {
			AsyncFirewallSupport firewallSupport = getNetworkSupport(context.getProjectId());
			Iterable<Firewall> iterable = firewallSupport.list().get();
			Iterator<Firewall> iterator = iterable.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				Firewall firewall = iterator.next();
				if (securityGroups.contains(firewall.getName())) {
					firewallIds[i++] = firewall.getProviderFirewallId();
				}
			}
		} catch (UnsupportedOperationException e) {
			// ignore
		}

		withLaunchOptions.behindFirewalls(firewallIds);
		withLaunchOptions.withUserData(options.getUserData());
		withLaunchOptions.withBootstrapKey(options.getKeyName());
		Map<String, Object> metadata = new HashMap<String, Object>();
		for (String key : options.getMetadata().keySet()) {
			metadata.put(key, options.getMetadata().get(key));
		}
		withLaunchOptions.withMetaData(metadata);
		if (requestedNetworks != null && !requestedNetworks.isEmpty()) {
			withLaunchOptions.inSubnet(null, null, requestedNetworks.get(0).getId(), null);
		}
		Iterable<String> ids = getSupport(context.getProjectId()).launchMany(withLaunchOptions, num).get();
		return provisionInstances(context, options, ids);
	}

	private List<Instance> provisionInstances(NovaRequestContext context, CreateVmBaseOptions baseOptions,
			Iterable<String> ids) {
		List<Instance> instances = new ArrayList<Instance>();
		Iterator<String> iterator = ids.iterator();
		while (iterator.hasNext()) {
			String id = iterator.next();
			Instance instance = new Instance();
			// instance.setReservationId(baseOptions.getReservationId().toString());
			instance.setInstanceId(id);
			instance.setImageId(baseOptions.getImageRef());
			instance.setConfigDrive(String.valueOf(baseOptions.isConfigDrive()));
			instance.setUserId(baseOptions.getUserId());
			instance.setTenantId(baseOptions.getProjectId());
			instance.setFlavorId(baseOptions.getInstanceTypeId());
			instance.setName(baseOptions.getDisplayName());
			instance.setKeyName(baseOptions.getKeyName());
			instance.setMetadata(baseOptions.getMetadata());
			instance.setAccessIpV4(baseOptions.getAccessIpV4());
			instance.setAccessIpV6(baseOptions.getAccessIpV6());
			instance.setAvailabilityZone(baseOptions.getAvailabilityZone());
			instances.add(instance);
		}
		return instances;
	}

	@Override
	public void terminateInstance(NovaRequestContext context, Instance instance, List<String> reservations) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).terminate(instance.getInstanceId());
	}

	@Override
	public Instance updateInstance(NovaRequestContext context, String serverId, String name, String ipv4, String ipv6) {
		throw new WebApplicationException(Response.status(Status.NOT_IMPLEMENTED)
				.entity("update instance command not implemented yet").build());
	}

	@Override
	public void deleteInstanceMetadata(NovaRequestContext context, Instance instance, String key) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).removeTags(instance.getInstanceId(), new Tag(key, ""));
	}

	@Override
	public void updateInstanceMetadata(NovaRequestContext context, Instance instance, Map<String, String> metadata,
			boolean delete) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		Tag[] tags = new Tag[metadata.size()];
		int i = 0;
		for (Entry<String, String> meta : metadata.entrySet()) {
			tags[i++] = new Tag(meta.getKey(), meta.getValue());
		}
		if (delete) {
			getSupport(context.getProjectId()).setTags(instance.getInstanceId(), tags);
		} else {
			getSupport(context.getProjectId()).updateTags(instance.getInstanceId(), tags);
		}
	}

	private CachedVirtualMachineSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasVirtualMachineSupport()) {
				return provider.getComputeServices().getVirtualMachineSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	private AsyncFirewallSupport getNetworkSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasNetworkServices()) {
			if (provider.getNetworkServices().hasFirewallSupport()) {
				return provider.getNetworkServices().getFirewallSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	@Override
	public void
			resizeInstance(NovaRequestContext context, Instance instance, String newInstanceTypeId, boolean cleanShutdown)
					throws Exception {
		getSupport(context.getProjectId()).alterVirtualMachineProduct(instance.getInstanceId(), newInstanceTypeId);
	}

}
