package com.infinities.nova.api.openstack.compute.task;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.servers.api.CreateVmBaseOptions;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.response.model.NetworkForCreate;

public interface ComputeTaskApi {

	public List<Instance> buildInstances(NovaRequestContext context, CreateVmBaseOptions options, int num, Image bootMeta,
			String adminPassword, List<Entry<String, String>> injectedFiles, List<NetworkForCreate> requestedNetworks,
			List<String> securityGroups) throws Exception;

	public void terminateInstance(NovaRequestContext context, Instance instance, List<String> reservations) throws Exception;

	public Instance updateInstance(NovaRequestContext context, String serverId, String name, String ipv4, String ipv6)
			throws Exception;

	public void deleteInstanceMetadata(NovaRequestContext context, Instance instance, String key) throws Exception;

	public void updateInstanceMetadata(NovaRequestContext context, Instance instance, Map<String, String> metadata,
			boolean delete) throws Exception;

	public void
			resizeInstance(NovaRequestContext context, Instance instance, String newInstanceTypeId, boolean cleanShutdown)
					throws Exception;
}
