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
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dasein.cloud.compute.VmState;
import org.dasein.cloud.network.IPVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.servers.model.Server;
import com.infinities.nova.servers.model.Server.Addresses;
import com.infinities.nova.servers.model.Server.Addresses.Address;
import com.infinities.skyport.compute.entity.NovaStyleVirtualMachine;
import com.infinities.skyport.network.SkyportRawAddress;

/**
 * @author pohsun
 *
 */
public class ServerUtils {

	private static final Logger logger = LoggerFactory.getLogger(ServerUtils.class);
	static {
		Map<VmState, Map<String, String>> siteMap = new HashMap<VmState, Map<String, String>>();
		Map<String, String> active = new HashMap<String, String>();
		active.put("default", "ACTIVE");
		active.put(TaskStates.REBOOTING, "REBOOT");
		active.put(TaskStates.REBOOT_PENDING, "REBOOT");
		active.put(TaskStates.REBOOT_STARTED, "REBOOT");
		active.put(TaskStates.REBOOTING_HARD, "HARD_REBOOT");
		active.put(TaskStates.REBOOT_PENDING_HARD, "HARD_REBOOT");
		active.put(TaskStates.REBOOT_STARTED_HARD, "HARD_REBOOT");
		active.put(TaskStates.UPDATING_PASSWORD, "PASSWORD");
		active.put(TaskStates.REBUILDING, "REBUILD");
		active.put(TaskStates.REBUILD_BLOCK_DEVICE_MAPPING, "REBUILD");
		active.put(TaskStates.REBUILD_SPAWNING, "REBUILD");
		active.put(TaskStates.MIGRATING, "MIGRATING");
		active.put(TaskStates.RESIZE_PREP, "RESIZE");
		active.put(TaskStates.RESIZE_MIGRATING, "RESIZE");
		active.put(TaskStates.RESIZE_MIGRATED, "RESIZE");
		active.put(TaskStates.RESIZE_FINISH, "RESIZE");
		siteMap.put(VmState.RUNNING, active);

		Map<String, String> building = new HashMap<String, String>();
		building.put("default", "BUILD");
		siteMap.put(VmState.PENDING, building);

		Map<String, String> stopped = new HashMap<String, String>();
		stopped.put("default", "SHUTOFF");
		stopped.put(TaskStates.RESIZE_PREP, "RESIZE");
		stopped.put(TaskStates.RESIZE_MIGRATING, "RESIZE");
		stopped.put(TaskStates.RESIZE_MIGRATED, "RESIZE");
		stopped.put(TaskStates.RESIZE_FINISH, "RESIZE");
		siteMap.put(VmState.STOPPED, stopped);

		Map<String, String> paused = new HashMap<String, String>();
		paused.put("default", "PAUSED");
		siteMap.put(VmState.PAUSED, paused);

		Map<String, String> suspended = new HashMap<String, String>();
		suspended.put("default", "SUSPENDED");
		siteMap.put(VmState.SUSPENDED, suspended);

		Map<String, String> error = new HashMap<String, String>();
		error.put("default", "ERROR");
		siteMap.put(VmState.ERROR, error);

		Map<String, String> deleted = new HashMap<String, String>();
		deleted.put("default", "DELETED");
		siteMap.put(VmState.TERMINATED, deleted);
		STATE_MAP = Collections.unmodifiableMap(siteMap);
	}
	private static final Map<VmState, Map<String, String>> STATE_MAP;


	private ServerUtils() {

	}

	/**
	 * @param vm
	 * @return
	 */
	public static Server toServer(NovaStyleVirtualMachine vm) {
		String ipV4 = null;
		String ipV6 = null;
		Server server = new Server();
		server.setId(vm.getProviderVirtualMachineId());
		server.setName(vm.getName());

		VmState vmState = vm.getCurrentState();
		String taskState = vm.getTags().get("OS-EXT-STS:task_state");
		server.setStatus(statusFromState(vmState, taskState));
		String projectId = vm.getProviderOwnerId();
		if (Strings.isNullOrEmpty(projectId)) {
			projectId = "";
		}
		server.setTenantId(projectId);
		server.setUserId("");
		server.setMetadata(vm.getTags());
		String hostId = vm.getTags().get("host");
		logger.debug("hostId:{}", hostId);
		if (Strings.isNullOrEmpty(hostId)) {
			hostId = "";
		}
		server.setHostId(hostId);
		com.infinities.nova.servers.model.Server.Image image = new com.infinities.nova.servers.model.Server.Image();
		image.setId(vm.getProviderMachineImageId());
		server.setImage(image);
		com.infinities.nova.servers.model.Server.Flavor flavor = new com.infinities.nova.servers.model.Server.Flavor();
		flavor.setId(vm.getProductId());
		server.setFlavor(flavor);
		if (vm.getCreationTimestamp() > 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(vm.getCreationTimestamp());
			server.setCreated(calendar);
		}

		Addresses addresses = new Addresses();
		String ipv4 = null;
		String ipv6 = null;
		logger.debug("public address size:{}", vm.getPublicAddresses().length);
		for (SkyportRawAddress raw : vm.getPublicAddresses()) {
			Address address = new Address();
			address.setType("floating");
			address.setAddr(raw.getIpAddress());
			address.setMacAddr(raw.getMacAddress());
			if (IPVersion.IPV4.equals(raw.getVersion())) {
				address.setVersion(4);
			} else if (IPVersion.IPV6.equals(raw.getVersion())) {
				address.setVersion(6);
			}

			List<Address> list = addresses.getAddresses().get(raw.getVlanName());
			if (list == null) {
				list = new ArrayList<Address>();
				addresses.getAddresses().put(raw.getVlanName(), list);
			}
			list.add(address);

			if (Strings.isNullOrEmpty(ipv4) && IPVersion.IPV4.equals(raw.getVersion())) {
				ipv4 = raw.getIpAddress();
			}
			if (Strings.isNullOrEmpty(ipv6) && IPVersion.IPV6.equals(raw.getVersion())) {
				ipv6 = raw.getIpAddress();
			}
		}
		logger.debug("private address size:{}", vm.getPrivateAddresses().length);
		for (SkyportRawAddress raw : vm.getPrivateAddresses()) {
			Address address = new Address();
			address.setType("fixed");
			address.setAddr(raw.getIpAddress());
			address.setMacAddr(raw.getMacAddress());
			if (IPVersion.IPV4.equals(raw.getVersion())) {
				address.setVersion(4);
			} else if (IPVersion.IPV6.equals(raw.getVersion())) {
				address.setVersion(6);
			}
			List<Address> list = addresses.getAddresses().get(raw.getVlanName());
			if (list == null) {
				list = new ArrayList<Address>();
				addresses.getAddresses().put(raw.getVlanName(), list);
				logger.debug("vlan name:{}", raw.getVlanName());
			}
			list.add(address);
			if (Strings.isNullOrEmpty(ipv4) && IPVersion.IPV4.equals(raw.getVersion())) {
				ipv4 = raw.getIpAddress();
			}
			if (Strings.isNullOrEmpty(ipv6) && IPVersion.IPV6.equals(raw.getVersion())) {
				ipv6 = raw.getIpAddress();
			}
		}

		server.setAddresses(addresses);
		logger.debug("server addresses size: {}", server.getAddresses().getAddresses().size());
		if (Strings.isNullOrEmpty(ipV4)) {
			ipV4 = "";
		}
		server.setAccessIPv4(ipV4);
		if (Strings.isNullOrEmpty(ipV6)) {
			ipV6 = "";
		}
		server.setAccessIPv6(ipV6);
		return server;
	}

	private static String statusFromState(VmState vmState, String taskState) {
		if (Strings.isNullOrEmpty(taskState)) {
			taskState = "default";
		}
		Map<String, String> taskMap = STATE_MAP.get(vmState);
		if (taskMap == null) {
			taskMap = new HashMap<String, String>();
			taskMap.put("default", "UNKNOWN");
		}
		String status = taskMap.get(taskState);
		if (Strings.isNullOrEmpty(status)) {
			status = taskMap.get("default");
		}
		if ("UNKNOWN".equals(status)) {
			logger.error("status is UNKNOWN from vm_state={} task_state={}. Bad upgrade or db corrupted?", new Object[] {
					vmState, taskState });
		}

		return status;
	}

}
