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
package com.infinities.nova;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.common.Config;
import com.infinities.nova.compute.TaskStates;
import com.infinities.nova.compute.VmStates;
import com.infinities.nova.db.model.Address;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.exception.InstanceInvalidStateException;
import com.infinities.nova.exception.http.HTTPBadRequestException;
import com.infinities.nova.exception.http.HTTPConflictException;

public class Common {

	// private static final QuotaEngine quotas = QuotaEngine.getQUOTAS();
	private static final Logger logger = LoggerFactory.getLogger(Common.class);
	private static final Map<String, Map<String, String>> STATE_MAP;

	// private final static boolean DHCP = false;

	static {
		Map<String, Map<String, String>> siteMap = new HashMap<String, Map<String, String>>();
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
		siteMap.put(VmStates.ACTIVE, active);

		Map<String, String> building = new HashMap<String, String>();
		building.put("default", "BUILD");
		siteMap.put(VmStates.BUILDING, building);

		Map<String, String> stopped = new HashMap<String, String>();
		stopped.put("default", "SHUTOFF");
		stopped.put(TaskStates.RESIZE_PREP, "RESIZE");
		stopped.put(TaskStates.RESIZE_MIGRATING, "RESIZE");
		stopped.put(TaskStates.RESIZE_MIGRATED, "RESIZE");
		stopped.put(TaskStates.RESIZE_FINISH, "RESIZE");
		siteMap.put(VmStates.STOPPED, stopped);

		Map<String, String> resized = new HashMap<String, String>();
		resized.put("default", "VERIFY_RESIZE");
		resized.put(TaskStates.RESIZE_REVERTING, "REVERT_RESIZE");
		siteMap.put(VmStates.RESIZED, resized);

		Map<String, String> paused = new HashMap<String, String>();
		paused.put("default", "PAUSED");
		siteMap.put(VmStates.PAUSED, paused);

		Map<String, String> suspended = new HashMap<String, String>();
		suspended.put("default", "SUSPENDED");
		siteMap.put(VmStates.SUSPENDED, suspended);

		Map<String, String> rescued = new HashMap<String, String>();
		rescued.put("default", "RESCUE");
		siteMap.put(VmStates.RESCUED, rescued);

		Map<String, String> error = new HashMap<String, String>();
		error.put("default", "ERROR");
		siteMap.put(VmStates.ERROR, error);

		Map<String, String> deleted = new HashMap<String, String>();
		deleted.put("default", "DELETED");
		siteMap.put(VmStates.DELETED, deleted);

		Map<String, String> softDeleted = new HashMap<String, String>();
		softDeleted.put("default", "SOFT_DELETED");
		siteMap.put(VmStates.SOFT_DELETED, softDeleted);

		Map<String, String> shelved = new HashMap<String, String>();
		shelved.put("default", "SHELVED");
		siteMap.put(VmStates.SHELVED, shelved);

		Map<String, String> shelvedOffloaded = new HashMap<String, String>();
		shelvedOffloaded.put("default", "SHELVED_OFFLOADED");
		siteMap.put(VmStates.SHELVED_OFFLOADED, shelvedOffloaded);

		STATE_MAP = Collections.unmodifiableMap(siteMap);
	}


	private Common() {

	}

	public static PaginationParams getPaginationParams(ContainerRequestContext requestContext) {
		PaginationParams params = new PaginationParams();
		MultivaluedMap<String, String> parameters = requestContext.getUriInfo().getQueryParameters();

		if (parameters.containsKey("limit")) {
			params.setLimit(getIntParam(parameters.getFirst("limit"), "limit"));
		}
		if (parameters.containsKey("page_size")) {
			params.setPageSize(getIntParam(parameters.getFirst("page_size"), "page_size"));
		}
		if (parameters.containsKey("marker")) {
			params.setMarker(getMarkerParam(requestContext));
		}

		return params;
	}

	private static String getMarkerParam(ContainerRequestContext requestContext) {
		return requestContext.getUriInfo().getQueryParameters().getFirst("marker");
	}

	private static int getIntParam(String first, String param) {
		int intParam;
		try {
			intParam = Integer.parseInt(first);
		} catch (Exception e) {
			String msg = String.format("%s param must be an integer", param);
			throw new HTTPBadRequestException(msg);
		}

		if (intParam < 0) {
			String msg = String.format("%s param must be positive", param);
			throw new HTTPBadRequestException(msg);
		}

		return intParam;
	}

	public static void checkImgMetadataPropertiesQuota(NovaRequestContext context, Map<String, String> metadata)
			throws Exception {
		return;
		// if (metadata == null) {
		// return;
		// }
		//
		// try {
		// Map<String, Integer> values = new HashMap<String, Integer>();
		// values.put("metadata_items", metadata.size());
		// quotas.limitCheck(context, null, null, values);
		// } catch (WebApplicationException e) {
		// if (e.getResponse().getStatus() == 500) {
		// String msg = "Image metadata limit exceeded";
		// throw new HTTPForbiddenException(msg);
		// }
		// throw e;
		// }
		//
		// for (Entry<String, String> entry : metadata.entrySet()) {
		// if (Strings.isNullOrEmpty(entry.getKey())) {
		// String msg = "Image metadata key cannot be blank";
		// throw new HTTPBadRequestException(msg);
		// }
		//
		// if (entry.getKey().length() > 255) {
		// String msg = "Image metadata key too long";
		// throw new HTTPBadRequestException(msg);
		// }
		// }

	}

	public static Entry<List<String>, List<String>> taskAndVmStateFromStatus(List<String> statuses) {
		Set<String> vmStates = new HashSet<String>();
		Set<String> taskStates = new HashSet<String>();
		Set<String> lowerStatuses = new HashSet<String>();
		for (String status : statuses) {
			lowerStatuses.add(status.toLowerCase());
		}

		for (Entry<String, Map<String, String>> entry : STATE_MAP.entrySet()) {
			String state = entry.getKey();
			Map<String, String> taskMap = entry.getValue();
			for (Entry<String, String> entry2 : taskMap.entrySet()) {
				String taskState = entry2.getKey();
				String mappedState = entry2.getValue();
				String statusString = mappedState;
				if (lowerStatuses.contains(statusString.toLowerCase())) {
					vmStates.add(state);
					taskStates.add(taskState);
				}
			}
		}
		List<String> vmStatesList = Lists.newArrayList(vmStates);
		Collections.sort(vmStatesList);

		List<String> taskStatesList = Lists.newArrayList(taskStates);
		Collections.sort(taskStatesList);

		return Maps.immutableEntry(vmStatesList, taskStatesList);
	}

	public static String statusFromState(String vmState, String taskState) {
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

	public static String getIdFromHref(String imageRef) throws URISyntaxException {
		URI uri = new URI(imageRef);
		String path = uri.getPath();
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		String[] parts = path.split("/");

		return parts[parts.length - 1];
	}

	// nova.api.openstack.common
	public static Map<String, CommonNetwork> getNetworksForInstance(NovaRequestContext context, Instance instance)
			throws UnknownHostException, CloneNotSupportedException {
		List<Address> nwInfo = instance.getAddresses();
		return getNetworksForInstanceFromNwInfo(nwInfo);
	}

	public static Map<String, CommonNetwork> getNetworksForInstanceFromNwInfo(List<Address> nwInfo) {
		Map<String, CommonNetwork> networks = new HashMap<String, CommonNetwork>();
		for (Address vif : nwInfo) {
			String label = vif.getNetwork();
			if (!networks.containsKey(label)) {
				networks.put(label, new CommonNetwork());
			}
			networks.get(label).getIps().add(vif);
		}
		return networks;
	}

	public static Entry<Integer, String> getLimitAndMarker(ContainerRequestContext request) {
		return getLimitAndMarker(request, null);
	}

	public static Entry<Integer, String> getLimitAndMarker(ContainerRequestContext request, String maxLimitStr) {
		PaginationParams params = getPaginationParams(request);
		Integer maxLimit;
		if (Strings.isNullOrEmpty(maxLimitStr)) {
			maxLimit = Config.Instance.getOpt(Config.Type.DEFAULT, "osapi_max_limit").asInteger();
		} else {
			maxLimit = Integer.parseInt(maxLimitStr);
		}

		int limit =
				params.getLimit() != null ? params.getLimit() : Config.Instance.getOpt(Config.Type.DEFAULT,
						"osapi_max_limit").asInteger();
		limit = Math.min(maxLimit, limit);
		String marker = params.getMarker();
		return Maps.immutableEntry(maxLimit, marker);

	}


	public static class CommonNetwork {

		private Set<Address> ips = new HashSet<Address>();
		private Set<Address> floatingIps = new HashSet<Address>();


		public Set<Address> getIps() {
			return ips;
		}

		public void setIps(Set<Address> ips) {
			this.ips = ips;
		}

		public Set<Address> getFloatingIps() {
			return floatingIps;
		}

		public void setFloatingIps(Set<Address> floatingIps) {
			this.floatingIps = floatingIps;
		}

	}

	public static class PaginationParams {

		private Integer limit;
		private Integer pageSize;
		private String marker;


		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}

		public String getMarker() {
			return marker;
		}

		public void setMarker(String marker) {
			this.marker = marker;
		}

	}


	public static void raiseHttpConflictForInstanceInvalidState(InstanceInvalidStateException e, String action) {
		Object attr = e.getAttr();
		Object state = e.getState();
		Boolean notLaunched = e.getNotLauched();
		String msg;
		if (attr != null && state != null) {
			msg = String.format("Cannot '%s' while instance is in %s %s", action, attr, state);
		} else if (notLaunched != null) {
			msg = String.format("Cannot '%s' an instance which has never been active", action);
		} else {
			msg = String.format("Instance is in an invalid state for %s", action);
		}

		throw new HTTPConflictException(msg);

	}
}
