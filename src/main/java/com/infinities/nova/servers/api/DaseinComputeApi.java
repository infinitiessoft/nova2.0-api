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
package com.infinities.nova.servers.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.inject.Inject;

import jersey.repackaged.com.google.common.collect.Maps;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.ImageCreateOptions;
import org.dasein.cloud.compute.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.Policy;
import com.infinities.nova.db.model.Address;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.db.model.InstanceType;
import com.infinities.nova.exception.CannotResizeToSameFlavorException;
import com.infinities.nova.exception.FlavorNotFoundException;
import com.infinities.nova.exception.InstanceNotFoundException;
import com.infinities.nova.exception.InvalidFixedIpAndMaxCountRequestException;
import com.infinities.nova.exception.InvalidInputException;
import com.infinities.nova.exception.http.HTTPNotImplementedException;
import com.infinities.nova.flavors.api.FlavorsApi;
import com.infinities.nova.images.api.ImagesApi;
import com.infinities.nova.policy.Target;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.response.model.NetworkForCreate;
import com.infinities.nova.servers.controller.ServersFilter;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedMachineImageSupport;
import com.infinities.skyport.cache.service.compute.CachedVirtualMachineSupport;
import com.infinities.skyport.compute.entity.NovaStyleVirtualMachine;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinComputeApi implements ComputeApi {

	// private static final QuotaEngine quotas = QuotaEngine.getQUOTAS();

	private final ComputeTaskApi computeTaskApi;
	private final Logger logger = LoggerFactory.getLogger(DaseinComputeApi.class);

	private ConfigurationHome configurationHome;
	private final FlavorsApi flavorsApi;
	private final ImagesApi imagesApi;


	@Inject
	public DaseinComputeApi(ConfigurationHome configurationHome, ComputeTaskApi computeTaskApi, FlavorsApi flavorsApi,
			ImagesApi imagesApi) {
		this.configurationHome = configurationHome;
		this.computeTaskApi = computeTaskApi;
		this.flavorsApi = flavorsApi;
		this.imagesApi = imagesApi;
	}

	@Override
	public List<Instance> getAll(NovaRequestContext context, ServersFilter filters, String sortKey, String sortDir,
			Integer limit, String marker, List<String> expectedAttrs) throws Exception {
		if (Strings.isNullOrEmpty(sortKey)) {
			sortKey = "created_at";
		}
		if (Strings.isNullOrEmpty(sortDir)) {
			sortDir = "desc";
		}
		checkPolicy(context, "get_all", context, null);

		if (filters == null) {
			filters = new ServersFilter();
		}

		List<Instance> instModels = getInstancesByFilters(context, filters, sortKey, sortDir, limit, marker, expectedAttrs);

		if (!Strings.isNullOrEmpty(filters.getIp())) {
			instModels = ipFilter(instModels, filters);
		}

		return instModels;
	}

	private List<Instance> ipFilter(List<Instance> instModels, ServersFilter filters) {
		Pattern ipV4F = Pattern.compile(filters.getIp());
		List<Instance> resultObjs = new ArrayList<Instance>();
		for (Instance instance : instModels) {
			for (Address address : instance.getAddresses()) {
				String addr = address.getAddr();
				if (Strings.isNullOrEmpty(addr)) {
					continue;
				}
				String version = address.getIpVersion();
				if (version.equals("4") && ipV4F.matcher(addr).matches()) {
					resultObjs.add(instance);
					continue;
				}
			}
		}
		return resultObjs;
	}

	// limit=null, marker=null
	private List<Instance> getInstancesByFilters(NovaRequestContext context, ServersFilter filters, String sortKey,
			String sortDir, Integer limit, String marker, List<String> expectedAttrs) throws InternalException,
			CloudException, InterruptedException, ExecutionException, ConcurrentException {
		List<String> fields = new ArrayList<String>();
		fields.add("metadata");
		fields.add("security_groups"); // security_groups
		if (expectedAttrs != null) {
			fields.addAll(expectedAttrs);
		}
		return instanceList_GetByFilters(context, filters, sortKey, sortDir, limit, marker, fields);
	}

	private List<Instance> instanceList_GetByFilters(NovaRequestContext context, ServersFilter filters, String sortKey,
			String sortDir, Integer limit, String marker, List<String> expectedAttrs) throws InternalException,
			CloudException, InterruptedException, ExecutionException, ConcurrentException {
		if (Strings.isNullOrEmpty(sortKey)) {
			sortKey = "created_at";
		}
		if (Strings.isNullOrEmpty(sortDir)) {
			sortDir = "desc";
		}

		AsyncResult<Iterable<NovaStyleVirtualMachine>> result =
				getSupport(context.getProjectId()).listNovaStyleVirtualMachines();
		Iterable<NovaStyleVirtualMachine> iterable = result.get();
		Iterator<NovaStyleVirtualMachine> iterator = iterable.iterator();

		List<Instance> instances = new ArrayList<Instance>();
		while (iterator.hasNext()) {
			NovaStyleVirtualMachine vm = iterator.next();
			instances.add(Instance.toInstance(vm));
		}

		return makeInstanceList(context, instances);
	}

	private List<Instance> makeInstanceList(NovaRequestContext context, List<Instance> dbInstList) {
		// pass
		return dbInstList;
	}

	private void checkPolicy(NovaRequestContext context, String action, Target target, String scope) throws Exception {
		if (Strings.isNullOrEmpty(scope)) {
			scope = "compute";
		}
		String _action = String.format("%s:%s", scope, action);
		Policy.enforce(context, _action, target, true, null);
	}

	@Override
	public Instance get(NovaRequestContext context, String serverId, List<String> expectedAttrs) throws Exception {
		if (expectedAttrs == null) {
			expectedAttrs = new ArrayList<String>();
		}
		expectedAttrs.add("metadata");
		expectedAttrs.add("security_groups");
		Instance instance = null;

		AsyncResult<VirtualMachine> result = getSupport(context.getProjectId()).getVirtualMachine(serverId);
		VirtualMachine vm = result.get();
		if (vm == null) {
			throw new InstanceNotFoundException(null, serverId);
		}
		instance = Instance.toInstance(vm);
		checkPolicy(context, "get", instance, null);
		return instance;
	}

	@Override
	public Entry<List<Instance>, UUID> create(NovaRequestContext context, String flavorId, String imageHref,
			String kernelId, String ramDiskId, Integer minCount, Integer maxCount, String displayName,
			String displayDescription, String keyName, String keyData, List<String> securityGroup, String availabilityZone,
			String userData, Map<String, String> metadata, List<Entry<String, String>> injectedFiles, String adminPassword,
			String accessIpV4, String accessIpV6, List<NetworkForCreate> requestedNetworks, boolean configDrive,
			boolean autoDiskConfig, boolean checkServerGroupQuota) throws Exception {

		checkCreatePolicies(context, availabilityZone, requestedNetworks);

		if (requestedNetworks != null && maxCount > 1) {
			checkMultipleInstancesAndSpecifiedIp(requestedNetworks);
		}

		boolean shutdownTerminate = false;
		return createInstance(context, flavorId, imageHref, kernelId, ramDiskId, minCount, maxCount, displayName,
				displayDescription, keyName, keyData, securityGroup, availabilityZone, userData, metadata, injectedFiles,
				adminPassword, accessIpV4, accessIpV6, requestedNetworks, configDrive, autoDiskConfig, shutdownTerminate,
				checkServerGroupQuota);
	}

	private Entry<List<Instance>, UUID> createInstance(NovaRequestContext context, String flavorId, String imageHref,
			String kernelId, String ramDiskId, Integer minCount, Integer maxCount, String displayName,
			String displayDescription, String keyName, String keyData, List<String> securityGroups, String availabilityZone,
			String userData, Map<String, String> metadata, List<Entry<String, String>> injectedFiles, String adminPassword,
			String accessIpV4, String accessIpV6, List<NetworkForCreate> requestedNetworks, boolean configDrive,
			boolean autoDiskConfig, boolean shutdownTerminate, boolean checkServerGroupQuota) throws Exception {
		UUID reservationId = UUID.randomUUID();
		if (securityGroups == null || securityGroups.isEmpty()) {
			securityGroups = new ArrayList<String>();
			securityGroups.add("default");
		}

		if (minCount == null) {
			minCount = 1;
		}

		if (maxCount == null) {
			maxCount = minCount;
		}

		String imageId = null;
		com.infinities.nova.response.model.Image bootMeta = null;
		if (!Strings.isNullOrEmpty(imageHref)) {
			bootMeta = getImage(context, imageHref);
			imageId = bootMeta.getId();
		}

		InstanceType instanceType = flavorsApi.getFlavorByFlavorId(flavorId, context, "no");

		AvailabilityZone az = handleAvailabilityZone(context, availabilityZone);
		availabilityZone = az.getZone();
		String forcedHost = az.getHost();
		String forcedNode = az.getNode();

		if (!Strings.isNullOrEmpty(forcedHost) || !Strings.isNullOrEmpty(forcedNode)) {
			checkPolicy(context, "create:forced_host", new Target() {

				@Override
				public String getName() {
					return "check create:forced_host";
				}

			}, null);
		}

		Entry<CreateVmBaseOptions, Integer> options =
				validateAndBuildBaseOptions(context, instanceType, bootMeta, imageHref, imageId, kernelId, ramDiskId,
						displayName, displayDescription, keyName, keyData, securityGroups, availabilityZone, forcedHost,
						userData, metadata, injectedFiles, accessIpV4, accessIpV6, requestedNetworks, configDrive,
						autoDiskConfig, reservationId, maxCount);
		CreateVmBaseOptions baseOptions = options.getKey();
		int num = options.getValue();

		List<Instance> instances =
				computeTaskApi.buildInstances(context, baseOptions, num, bootMeta, adminPassword, injectedFiles,
						requestedNetworks, securityGroups);

		return Maps.immutableEntry(instances, reservationId);
	}

	private Entry<CreateVmBaseOptions, Integer> validateAndBuildBaseOptions(NovaRequestContext context,
			InstanceType instType, Image bootMeta, String imageHref, String imageId, String kernelId, String ramDiskId,
			String displayName, String displayDescription, String keyName, String keyData, List<String> securityGroups,
			String availabilityZone, String forcedHost, String userData, Map<String, String> metadata,
			List<Entry<String, String>> injectedFiles, String accessIpV4, String accessIpV6,
			List<NetworkForCreate> requestedNetworks, boolean configDrive, boolean autoDiskConfig, UUID reservationId,
			Integer maxCount) {
		CreateVmBaseOptions baseOptions = new CreateVmBaseOptions();
		if (Strings.isNullOrEmpty(kernelId)) {
			kernelId = "";
		}
		if (Strings.isNullOrEmpty(ramDiskId)) {
			ramDiskId = "";
		}
		if (Strings.isNullOrEmpty(displayDescription)) {
			displayDescription = "";
		}
		if (metadata == null) {
			metadata = new HashMap<String, String>();
		}
		String rootDeviceName = bootMeta.getMetadata().get("root_device_name");
		String pciRequestInfo = null;
		String numaTopology = null;
		Map<String, String> systemMetadata = new HashMap<String, String>();
		baseOptions.setReservationId(reservationId);
		baseOptions.setImageRef(imageHref);
		baseOptions.setKernelId(kernelId);
		baseOptions.setRamDiskId(ramDiskId);
		baseOptions.setPowerState(PowerState.NOSTATE);
		baseOptions.setVmState(VmStates.BUILDING);
		baseOptions.setConfigDrive(configDrive);
		baseOptions.setUserId(context.getUserId());
		baseOptions.setProjectId(context.getProjectId());
		baseOptions.setInstanceTypeId(instType.getFlavorid());
		baseOptions.setMemoryMb(instType.getMemoryMb());
		baseOptions.setVpus(instType.getVcpus());
		baseOptions.setRootGb(instType.getRootGb());
		baseOptions.setEphemeralGb(instType.getEphemeralGb());
		baseOptions.setDisplayName(displayName);
		baseOptions.setDisplayDescription(displayDescription);
		baseOptions.setUserData(userData);
		baseOptions.setKeyName(keyName);
		baseOptions.setKeyData(keyData);
		baseOptions.setLocked(false);
		baseOptions.setMetadata(metadata);
		baseOptions.setAccessIpV4(accessIpV4);
		baseOptions.setAccessIpV6(accessIpV6);
		baseOptions.setAvailabilityZone(availabilityZone);
		baseOptions.setRootDeviceName(rootDeviceName);
		baseOptions.setProgress(0);
		baseOptions.setPciRequestInfo(pciRequestInfo);
		baseOptions.setNumaTopology(numaTopology);
		baseOptions.setSystemMetadata(systemMetadata);
		inheritPropertiesFromImage(baseOptions, bootMeta, autoDiskConfig);
		return Maps.immutableEntry(baseOptions, maxCount);
	}

	private void inheritPropertiesFromImage(CreateVmBaseOptions baseOptions, Image bootMeta, boolean autoDiskConfig) {
		Map<String, String> imageProperties = bootMeta.getMetadata();
		baseOptions.setOsType(imageProperties.get("os_type"));
		baseOptions.setArchitecture(imageProperties.get("architecture"));
		baseOptions.setVmMode(imageProperties.get("vmMode"));
		baseOptions.setAutoDiskConfig(autoDiskConfig);
	}

	private AvailabilityZone handleAvailabilityZone(NovaRequestContext context, String availabilityZone) {
		String host = null;
		String node = null;
		if (!Strings.isNullOrEmpty(availabilityZone) && availabilityZone.contains(":")) {
			String[] splits = availabilityZone.split(":");
			int c = splits.length - 1;
			if (c == 1) {
				availabilityZone = splits[0];
				host = splits[1];
			} else if (c == 2) {
				if (availabilityZone.contains("::")) {
					splits = availabilityZone.split("::");
					availabilityZone = splits[0];
					node = splits[1];
				} else {
					availabilityZone = splits[0];
					host = splits[1];
					node = splits[2];
				}
			} else {
				new InvalidInputException("Unable to parse availability_zone");
			}
		}
		AvailabilityZone ret = new AvailabilityZone();
		ret.setHost(host);
		ret.setNode(node);
		ret.setZone(availabilityZone);
		return ret;
	}

	private com.infinities.nova.response.model.Image getImage(NovaRequestContext context, String imageHref) throws Exception {
		if (Strings.isNullOrEmpty(imageHref)) {
			return null;
		}

		com.infinities.nova.response.model.Image image = imagesApi.get(context, imageHref);
		return image;
	}

	private void checkMultipleInstancesAndSpecifiedIp(List<NetworkForCreate> requestedNetworks) {
		String msg = "max count cannot be greater than 1 if an fixed_ip is specified.";
		throw new InvalidFixedIpAndMaxCountRequestException(msg);
	}

	private void checkCreatePolicies(NovaRequestContext context, String availabilityZone,
			List<NetworkForCreate> requestedNetworks) throws Exception {
		ComputeTarget target = new ComputeTarget();
		target.setProjectId(context.getProjectId());
		target.setUsertId(context.getUserId());
		target.setAvailabilityZone(availabilityZone);
		checkPolicy(context, "create", target, null);

		if (requestedNetworks != null && !requestedNetworks.isEmpty()) {
			checkPolicy(context, "create:attach_network", target, null);
		}
	}


	public class ComputeTarget implements Target {

		private String projectId;
		private String usertId;
		private String availabilityZone;


		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}

		public String getUsertId() {
			return usertId;
		}

		public void setUsertId(String usertId) {
			this.usertId = usertId;
		}

		public String getAvailabilityZone() {
			return availabilityZone;
		}

		public void setAvailabilityZone(String availabilityZone) {
			this.availabilityZone = availabilityZone;
		}

		@Override
		public String getName() {
			return "compute_target";
		}
	}


	@Override
	public void delete(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "delete", instance, "compute");
		logger.debug("Going to try to terminate instance: {}", instance.getInstanceId());
		computeTaskApi.terminateInstance(context, instance, null);
		// deleteInstance(context, instance);
	}

	@Override
	public Instance update(NovaRequestContext context, String serverId, String name, String ipv4, String ipv6)
			throws Exception {
		return computeTaskApi.updateInstance(context, serverId, name, ipv4, ipv6);
	}

	@Override
	public Map<String, String> getInstanceMetadata(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "get_instance_metadata", instance, null);
		return instance.getMetadata();
	}

	@Override
	public Map<String, String> updateInstanceMetadata(NovaRequestContext context, Instance instance,
			Map<String, String> metadata, boolean delete) throws Exception {
		checkPolicy(context, "update_instance_metadata", instance, null);

		Map<String, String> m;
		if (delete) {
			m = metadata;
		} else {
			m = new HashMap<String, String>();
			for (Entry<String, String> instanceMetadata : instance.getMetadata().entrySet()) {
				m.put(instanceMetadata.getKey(), instanceMetadata.getValue());
			}
			m.putAll(metadata);
		}

		computeTaskApi.updateInstanceMetadata(context, instance, metadata, delete);
		return m;
	}

	@Override
	public void deleteInstanceMetadata(NovaRequestContext context, Instance instance, String key) throws Exception {
		checkPolicy(context, "delete_instance_metadata", instance, null);
		computeTaskApi.deleteInstanceMetadata(context, instance, key);
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

	@Override
	public void resize(NovaRequestContext context, Instance instance, String flavorId, String autoDiskConfig)
			throws Exception {
		checkPolicy(context, "resize", instance, null);
		String currentInstanceTypeId = instance.getFlavorId();
		String newInstanceTypeId = null;
		InstanceType newInstanceType;
		if (Strings.isNullOrEmpty(flavorId)) {
			logger.debug("flavor_id is none. Assuming migration.");
			newInstanceTypeId = currentInstanceTypeId;
		} else {
			newInstanceType = flavorsApi.getFlavorByFlavorId(flavorId, context, "no");
			if (newInstanceType == null) {
				throw new FlavorNotFoundException(null, flavorId);
			}
			newInstanceTypeId = newInstanceType.getFlavorid();
		}

		boolean sameInstanceType = currentInstanceTypeId.equals(newInstanceTypeId);
		if (sameInstanceType) {
			throw new CannotResizeToSameFlavorException();
		}

		computeTaskApi.resizeInstance(context, instance, newInstanceTypeId, true);
	}

	@Override
	public void reboot(NovaRequestContext context, Instance instance, String rebootType) throws Exception {
		checkPolicy(context, "reboot", instance, null);
		if ("SOFT".equals(rebootType)) {
			softReboot(context, instance);
		} else {
			hardReboot(context, instance);
		}
	}

	private void hardReboot(NovaRequestContext context, Instance instance) throws CloudException, InternalException,
			ConcurrentException {
		this.getSupport(context.getProjectId()).reboot(instance.getInstanceId());
	}

	private void softReboot(NovaRequestContext context, Instance instance) {
		throw new HTTPNotImplementedException("service not supported");
	}

	@Override
	public void revertResize(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "revert_resize", instance, null);
		throw new HTTPNotImplementedException("service not supported");
	}

	@Override
	public void confirmResize(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "confirm_resize", instance, null);
		throw new HTTPNotImplementedException("service not supported");
	}

	@Override
	public void setAdminPassword(NovaRequestContext context, Instance instance, String adminPass) throws Exception {
		checkPolicy(context, "set_admin_password", instance, null);
		throw new HTTPNotImplementedException("service not supported");
	}

	@Override
	public void rebuild(NovaRequestContext context, Instance instance, String imageHref, String password, String accessIpV4,
			String accessIpV6, String name, Map<String, String> metadata, String diskConfig) throws Exception {
		checkPolicy(context, "rebuild", instance, null);
		throw new HTTPNotImplementedException("service not supported");
	}

	@Override
	public void snapshot(NovaRequestContext context, Instance instance, String imageName, Map<String, String> metadata)
			throws Exception {
		checkPolicy(context, "snapshot", instance, null);
		VirtualMachine fromVirtualMachine =
				this.getSupport(context.getProjectId()).getVirtualMachine(instance.getInstanceId()).get();
		ImageCreateOptions options = ImageCreateOptions.getInstance(fromVirtualMachine, imageName, null);
		this.getImageSupport(context.getProjectId()).captureImage(options);
	}

	private CachedMachineImageSupport getImageSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasImageSupport()) {
				return provider.getComputeServices().getImageSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	@Override
	public void pause(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "pause", instance, null);
		this.getSupport(context.getProjectId()).pause(instance.getInstanceId());
	}

	@Override
	public void unpause(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "unpause", instance, null);
		this.getSupport(context.getProjectId()).unpause(instance.getInstanceId());
	}

	@Override
	public void suspend(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "suspend", instance, null);
		this.getSupport(context.getProjectId()).suspend(instance.getInstanceId());
	}

	@Override
	public void resume(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "resume", instance, null);
		this.getSupport(context.getProjectId()).resume(instance.getInstanceId());
	}

	@Override
	public void start(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "resume", instance, null);
		this.getSupport(context.getProjectId()).start(instance.getInstanceId());
	}

	@Override
	public void stop(NovaRequestContext context, Instance instance) throws Exception {
		checkPolicy(context, "resume", instance, null);
		this.getSupport(context.getProjectId()).stop(instance.getInstanceId());
	}


	private class AvailabilityZone {

		private String host;
		private String node;
		private String zone;


		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getNode() {
			return node;
		}

		public void setNode(String node) {
			this.node = node;
		}

		public String getZone() {
			return zone;
		}

		public void setZone(String zone) {
			this.zone = zone;
		}

	}
}
