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
package com.infinities.nova.servers.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.infinities.api.openstack.commons.config.Config;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.exception.InvalidException;
import com.infinities.api.openstack.commons.exception.NotFoundException;
import com.infinities.api.openstack.commons.exception.http.HTTPBadRequestException;
import com.infinities.api.openstack.commons.exception.http.HTTPForbiddenException;
import com.infinities.api.openstack.commons.exception.http.HTTPNotFoundException;
import com.infinities.nova.AbstractPaginableController;
import com.infinities.nova.exception.CannotResizeToSameFlavorException;
import com.infinities.nova.exception.FlavorNotFoundException;
import com.infinities.nova.exception.ImageNotFoundException;
import com.infinities.nova.exception.InstanceNotFoundException;
import com.infinities.nova.exception.MarkerNotFoundException;
import com.infinities.nova.servers.api.ComputeApi;
import com.infinities.nova.servers.api.TaskStates;
import com.infinities.nova.servers.api.VmStates;
import com.infinities.nova.servers.model.CreatedServerTemplate;
import com.infinities.nova.servers.model.MinimalServersTemplate;
import com.infinities.nova.servers.model.NetworkRequest;
import com.infinities.nova.servers.model.PersonalityFile;
import com.infinities.nova.servers.model.Server;
import com.infinities.nova.servers.model.ServerAction;
import com.infinities.nova.servers.model.ServerAction.Pause;
import com.infinities.nova.servers.model.ServerAction.Resume;
import com.infinities.nova.servers.model.ServerAction.Start;
import com.infinities.nova.servers.model.ServerAction.Stop;
import com.infinities.nova.servers.model.ServerAction.Suspend;
import com.infinities.nova.servers.model.ServerAction.Unpause;
import com.infinities.nova.servers.model.ServerForCreate;
import com.infinities.nova.servers.model.ServerForCreate.SecurityGroup;
import com.infinities.nova.servers.model.ServerTemplate;
import com.infinities.nova.servers.model.ServersTemplate;
import com.infinities.nova.servers.views.ViewBuilder;
import com.infinities.nova.util.InetAddressUtils;
import com.infinities.nova.util.RandomStringGenerator;
import com.infinities.nova.util.RandomStringGenerator.Mode;
import com.infinities.nova.util.URLUtils;
import com.infinities.skyport.util.FormatUtil;

public class ServersControllerImpl extends AbstractPaginableController implements ServersController {

	private final static Logger logger = LoggerFactory.getLogger(ServersControllerImpl.class);
	// private final static List<String> SEARCH_OPTIONS;
	private final ComputeApi computeApi;
	private final ViewBuilder viewBuilder;
	private Config config;

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


	public ServersControllerImpl(Config config, ComputeApi computeApi) {
		super(config.getOpt("osapi_max_limit").asInteger());
		String osapiComputeLinkPrefix = config.getOpt("osapi_compute_link_prefix").asText();
		int osapiMaxLimit = config.getOpt("osapi_max_limit").asInteger();
		viewBuilder = new ViewBuilder(osapiComputeLinkPrefix, osapiMaxLimit);
		this.computeApi = computeApi;
		this.config = config;
	}

	@Override
	public MinimalServersTemplate index(ContainerRequestContext requestContext) throws Exception {
		try {
			List<Server> instances = getServers(requestContext);
			return viewBuilder.index(requestContext, instances);
		} catch (InvalidException e) {
			logger.error("servers index failed", e);
			throw new HTTPBadRequestException(e.getMessage());
		}
	}

	@Override
	public ServersTemplate detail(ContainerRequestContext requestContext) throws Exception {
		try {
			List<Server> instances = getServers(requestContext);
			return viewBuilder.detail(requestContext, instances);
		} catch (InvalidException e) {
			logger.error("servers detail failed", e);
			throw new HTTPBadRequestException(e.getMessage());
		}
	}

	@Override
	public ServerTemplate show(String serverId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		try {
			Server instance = computeApi.get(context, serverId, null);
			// requestContext.cache_db_instance(instance);
			return viewBuilder.show(requestContext, instance);
		} catch (NotFoundException e) {
			logger.error("servers show failed", e);
			throw new HTTPNotFoundException(e.getMessage());
		}
	}

	// search options: reservation_id, name, status, image, flavor, ip,
	// changes-since, all_tenants
	private List<Server> getServers(ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		ServersFilter filter = new ServersFilter();
		Map<String, String> searchOpts = new HashMap<String, String>();
		searchOpts.putAll(copy(requestContext.getUriInfo().getQueryParameters()));

		removeInvalidOptions(context, searchOpts, getServerSearchOptions());
		filter.setReservationId(searchOpts.get("reservation_id"));
		filter.setName(searchOpts.get("name"));
		filter.setImage(searchOpts.get("image"));
		filter.setFlavor(searchOpts.get("flavor"));
		filter.setIp(searchOpts.get("ip"));

		searchOpts.remove("status");
		if (requestContext.getUriInfo().getQueryParameters().containsKey("status")) {
			List<String> statuses = requestContext.getUriInfo().getQueryParameters().get("status");
			Entry<List<String>, List<String>> states = taskAndVmStateFromStatus(statuses);
			List<String> vmState = states.getKey();
			List<String> taskState = states.getValue();

			if ((vmState == null || vmState.isEmpty()) && (taskState == null || taskState.isEmpty())) {
				return new ArrayList<Server>();
			}
			filter.setVmState(vmState);

			if (!taskState.contains("default")) {
				filter.setTaskState(taskState);
			}

		}

		if (searchOpts.containsKey("changes-since")) {
			try {
				Calendar parsed = FormatUtil.getCalendar(searchOpts.get("changes-since"));
				filter.setChangesSince(parsed);
			} catch (Exception e) {
				String msg = "Invalid changes-since value";
				throw new HTTPBadRequestException(msg);
			}
		}

		if (!searchOpts.containsKey("deleted")) {
			if (!searchOpts.containsKey("changes-since")) {
				filter.setDeleted(false);
			}
		}

		List<String> deletedList = new ArrayList<String>();
		deletedList.add("deleted");
		if (deletedList.equals(searchOpts.get("vm_state"))) {
			if (context.getIsAdmin()) {
				filter.setDeleted(true);
			} else {
				String msg = "Only administrators may list deleted instances";
				throw new HTTPForbiddenException(msg);
			}
		}

		String allTenants = searchOpts.get("all_tenants");
		if (!Strings.isNullOrEmpty(allTenants)) {
			try {
				boolean bool = Boolean.parseBoolean(allTenants);
				if (!bool) {
					searchOpts.remove("all_tenants");
					filter.setAllTenants(null);
				} else {
					filter.setAllTenants(true);
				}
			} catch (Exception e) {
				String msg = e.getMessage();
				throw new HTTPForbiddenException(msg);
			}

		}

		if (searchOpts.containsKey("all_tenants")) {
			// Policy.enforce(context, "compute:get_all_tenants", context, true,
			// null);
			searchOpts.remove("all_tenants");
			filter.setAllTenants(null);
		} else {
			if (!Strings.isNullOrEmpty(context.getProjectId())) {
				searchOpts.put("project_id", context.getProjectId());
				filter.setProjectId(context.getProjectId());
			} else {
				searchOpts.put("user_id", context.getUserId());
				filter.setUserId(context.getUserId());
			}
		}

		Entry<Integer, String> entry = getLimitAndMarker(requestContext);
		Integer limit = entry.getKey();
		String marker = entry.getValue();

		List<Server> instanceList = null;

		try {
			instanceList = computeApi.getAll(context, filter, null, null, limit, marker, null);

			// } catch (MarkerNotFoundException e) {
			// String msg = String.format("marker [%s] not found", marker);
			// throw new
			// WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
		} catch (MarkerNotFoundException e) {
			logger.error("unexpected exception", e);
			instanceList = new ArrayList<Server>();
			String msg = String.format("marker [%s] not found", marker);
			throw new HTTPBadRequestException(msg);
		} catch (FlavorNotFoundException e) {
			logger.error("Flavor '{}' could not be found", filter.getFlavor());
			instanceList = new ArrayList<Server>();
		}

		// requestContext.cache_db_instances(instanceList);
		return instanceList;
	}

	private void removeInvalidOptions(OpenstackRequestContext context, Map<String, String> searchOpts,
			Set<String> allowedSearchOptions) {
		if (context.getIsAdmin()) {
			return;
		}

		Set<String> unknownOptions = Sets.difference(searchOpts.keySet(), allowedSearchOptions);
		String join = Joiner.on(", ").join(unknownOptions);
		logger.debug("Removing options '{}' from query", join);

		for (String opt : unknownOptions) {
			searchOpts.remove(opt);
		}
	}

	private Set<String> getServerSearchOptions() {
		Set<String> set = new HashSet<String>();
		set.add("reservation_id");
		set.add("name");
		set.add("status");
		set.add("image");
		set.add("flavor");
		set.add("ip");
		set.add("changes-since");
		set.add("all_tenants");
		return set;
	}

	private Map<? extends String, ? extends String> copy(MultivaluedMap<String, String> queryParameters) {
		Map<String, String> copy = new HashMap<String, String>();
		for (String key : queryParameters.keySet()) {
			copy.put(key, queryParameters.getFirst(key));
		}
		return copy;
	}

	@Override
	public Response create(ContainerRequestContext requestContext, ServerForCreate server) throws Exception {
		OpenstackRequestContext novaContext = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		String password = getServerAdminPassword(server);

		if (Strings.isNullOrEmpty(server.getName())) {
			String msg = "Server name is not defined";
			throw new HTTPBadRequestException(msg);
		}

		String name = server.getName();
		validateServerName(name);
		name = name.trim();

		String imageUuid = imageFromReqData(server);

		List<PersonalityFile> personality = server.getPersonality();
		List<Entry<String, String>> injectedFiles = new ArrayList<Entry<String, String>>();
		boolean configDrive = server.isConfigDrive();

		if (personality != null && !personality.isEmpty()) {
			injectedFiles = getInjectedFiles(personality);
		}

		List<String> sgNames = new ArrayList<String>();
		for (SecurityGroup sg : server.getSecurityGroups()) {
			if (!Strings.isNullOrEmpty(sg.getName())) {
				sgNames.add(sg.getName());
			}
		}

		if (sgNames.isEmpty()) {
			sgNames.add("default");
		}

		sgNames = new ArrayList<String>(new HashSet<String>(sgNames));

		List<ServerForCreate.NetworkForCreate> requestedNetworks = server.getNetworks();
		if (requestedNetworks != null && !requestedNetworks.isEmpty()) {
			getRequestedNetworks(requestedNetworks);
		}

		String accessIpV4 = server.getAccessIPv4();
		if (!Strings.isNullOrEmpty(accessIpV4)) {
			validateAccessIpv4(accessIpV4);
		}

		String accessIpV6 = server.getAccessIPv6();
		if (!Strings.isNullOrEmpty(accessIpV6)) {
			validateAccessIpv6(accessIpV6);
		}

		String flavorId = flavorIdFromReqData(server);

		String keyName = server.getKeyName();
		String userData = server.getUserData();
		validateUserData(userData);

		String availabilityZone = server.getAvailabilityZone();
		boolean autoDiskConfig = false;

		// TODO ignore blockDeviceMapping

		// boolean retResvId = false;
		Integer minCount = server.getMin();
		Integer maxCount = server.getMax();

		if (minCount == null) {
			minCount = 1;
		}
		if (maxCount == null) {
			maxCount = minCount;
		}

		if (minCount > maxCount) {
			String msg = "min_count must be <= max_count";
			throw new HTTPBadRequestException(msg);
		}

		// InstanceType instType =
		// instanceTypeHome.flavorGetByFlavorId(novaContext, flavorId, "no");

		boolean checkServerGroupQuota = true;

		Map<String, String> metadata = server.getMetadata();
		Entry<List<Server>, UUID> entry =
				computeApi.create(novaContext, flavorId, imageUuid, null, null, minCount, maxCount, name, name, keyName,
						null, sgNames, availabilityZone, userData, metadata, injectedFiles, password, accessIpV4,
						accessIpV6, requestedNetworks, configDrive, autoDiskConfig, checkServerGroupQuota);
		// .create(novaContext, instType, imageUuid, name, name, keyName,
		// metadata, accessIpV4, accessIpV6, injectedFiles, password, minCount,
		// maxCount, requestedNetworks,
		// sgNames, userData, availabilityZone, configDrive,
		// checkServerGroupQuota);
		List<Server> instances = entry.getKey();

		CreatedServerTemplate template = viewBuilder.create(requestContext, instances.get(0));

		return Response.status(Status.ACCEPTED).entity(template).build();
	}

	private void validateUserData(String userData) {
		if (Strings.isNullOrEmpty(userData)) {
			return;
		}

		if (Strings.isNullOrEmpty(decodeBase64(userData))) {
			String msg = "Userdata content cannot be decoded";
			throw new HTTPBadRequestException(msg);
		}
	}

	private String flavorIdFromReqData(ServerForCreate server) throws URISyntaxException {
		String flavorRef = server.getFlavorRef();
		if (Strings.isNullOrEmpty(flavorRef)) {
			String msg = "Invalid flavorRef provided.";
			throw new HTTPBadRequestException(msg);
		}

		return URLUtils.getIdFromHref(flavorRef);
	}

	private void validateAccessIpv6(String accessIpV6) {
		if (!InetAddressUtils.isIPv6Address(accessIpV6)) {
			String msg = "accessIPv6 is not proper IPv6 format";
			throw new HTTPBadRequestException(msg);

		}
	}

	private void validateAccessIpv4(String accessIpV4) {
		if (!InetAddressUtils.isIPv4Address(accessIpV4)) {
			String msg = "accessIPv4 is not proper IPv4 format";
			throw new HTTPBadRequestException(msg);

		}
	}

	private List<NetworkRequest> getRequestedNetworks(List<ServerForCreate.NetworkForCreate> requestedNetworks) {
		List<NetworkRequest> networks = new ArrayList<NetworkRequest>();
		List<String> networkUuids = new ArrayList<String>();

		for (ServerForCreate.NetworkForCreate network : requestedNetworks) {
			NetworkRequest request = new NetworkRequest();
			request.setPortId(null);
			request.setNetworkId(network.getId());
			request.setAddress(network.getFixedIp());

			if (!Strings.isNullOrEmpty(request.getNetworkId()) && networkUuids.contains(request.getNetworkId())) {
				String msg = String.format("Duplicate networks (%s) are not allowed", request.getNetworkId());
				throw new HTTPBadRequestException(msg);
			}

			networkUuids.add(request.getNetworkId());
			networks.add(request);
		}

		return networks;
	}

	private List<Entry<String, String>> getInjectedFiles(List<PersonalityFile> personality) {
		List<Entry<String, String>> injectedFiles = new ArrayList<Entry<String, String>>();

		for (PersonalityFile item : personality) {
			String path = item.getPath();
			String contents = item.getContents();
			if (Strings.isNullOrEmpty(path)) {
				String msg = "Bad personality format: missing path";
				throw new HTTPBadRequestException(msg);
			}

			if (Strings.isNullOrEmpty(contents)) {
				String msg = "Bad personality format: missing contents";
				throw new HTTPBadRequestException(msg);
			}

			if (Strings.isNullOrEmpty(decodeBase64(contents))) {
				String msg = String.format("Personality content for %s cannot be decoded", path);
				throw new HTTPBadRequestException(msg);
			}
			injectedFiles.add(Maps.immutableEntry(path, contents));
		}
		return injectedFiles;
	}

	private String decodeBase64(String contents) {
		contents = contents.replaceAll("r\'\\s\'", "");
		if (!contents.matches(contents)) {
			return null;
		}
		try {
			return new String(Base64.decode(contents));
		} catch (Exception e) {
			return null;
		}
	}

	private String imageFromReqData(ServerForCreate server) {
		// TODO ignore block_device_mapping
		String imageRef = server.getImageRef();
		String uuid = imageUuidFromHref(imageRef);

		return uuid;
	}

	private String imageUuidFromHref(String imageRef) {
		if (Strings.isNullOrEmpty(imageRef)) {
			String msg = "Invalid imageRef provided.";
			throw new HTTPBadRequestException(msg);
		}

		if (imageRef.contains("/")) {
			if (imageRef.endsWith("/")) {
				imageRef = imageRef.substring(0, imageRef.length() - 1);
			}
			String split[] = imageRef.split("/");
			imageRef = split[split.length - 1];
		}

		// try {
		// UUID.fromString(imageRef);
		// } catch (IllegalArgumentException e) {
		// String msg = "Invalid imageRef provided.";
		// throw new
		// WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
		// }

		return imageRef;
	}

	private void validateServerName(String name) {
		checkStringLength(name, "Server name", 255);
	}

	private void checkStringLength(String value, String name, int maxLength) {
		value = value.trim();
		checkStringLength(value, name, 1, maxLength);
	}

	// minLength=0
	private void checkStringLength(String value, String name, int minLength, int maxLength) {
		if (Strings.isNullOrEmpty(name)) {
			name = value;
		}

		if (value.length() < minLength) {
			String msg = String.format("%s has a minimum character requirement of %s.", name, minLength);
			throw new HTTPBadRequestException(msg);
		}

		if (value.length() > maxLength) {
			String msg = String.format("%s has more than %s.", name, minLength);
			throw new HTTPBadRequestException(msg);
		}
	}

	private String getServerAdminPassword(ServerForCreate server) {
		String password = server.getAdminPass();
		if (Strings.isNullOrEmpty(password)) {
			int length = config.getOpt("password_length").asInteger();
			password = RandomStringGenerator.generateRandomString(length, Mode.ALPHANUMERIC);
		}
		return password;
	}

	private String getServerAdminPassword(ServerAction.Rebuild body) {
		String password = body.getAdminPass();
		if (Strings.isNullOrEmpty(password)) {
			int length = config.getOpt("password_length").asInteger();
			password = RandomStringGenerator.generateRandomString(length, Mode.ALPHANUMERIC);
		}
		return password;
	}

	@Override
	public void delete(String serverId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		computeApi.delete(context, instance);
	}

	@Override
	public Response resize(String serverId, ContainerRequestContext requestContext, ServerAction.Resize body)
			throws Exception {
		String flavorRef = null;
		flavorRef = body.getFlavorRef();
		if (Strings.isNullOrEmpty(flavorRef)) {
			String msg = "Resize requests has invalid 'flavorRef' attribute.";
			throw new HTTPBadRequestException(msg);
		}

		String autoDiskConfig = null;

		if (!Strings.isNullOrEmpty(body.getDiskConfig())) {
			autoDiskConfig = body.getDiskConfig();
		}
		return resize(requestContext, serverId, flavorRef, autoDiskConfig);
	}

	@Override
	public Response reboot(String serverId, ContainerRequestContext requestContext, ServerAction.Reboot body)
			throws Exception {
		String rebootType = null;
		if (!Strings.isNullOrEmpty(body.getType())) {
			Set<String> validRebootTypes = new HashSet<String>();
			validRebootTypes.add("HARD");
			validRebootTypes.add("SOFT");
			rebootType = body.getType().toUpperCase();
			if (!validRebootTypes.contains(rebootType)) {
				String msg = "Argument 'type' for reboot is not HARD or SOFT";
				logger.error(msg);
				throw new HTTPBadRequestException(msg);
			}
		} else {
			String msg = "Missing argument 'type' for reboot";
			logger.error(msg);
			throw new HTTPBadRequestException(msg);
		}
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		computeApi.reboot(context, instance, rebootType);
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public ServerTemplate rebuild(String serverId, ContainerRequestContext requestContext, ServerAction.Rebuild body)
			throws Exception {
		String imageHref = null;
		imageHref = body.getImageRef();
		if (Strings.isNullOrEmpty(imageHref)) {
			String msg = "Could not parse imageRef from request.";
			throw new HTTPBadRequestException(msg);
		}

		imageHref = imageUuidFromHref(imageHref);
		String password = getServerAdminPassword(body);
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);

		String accessIpV4 = body.getAccessIPv4();
		validateAccessIpv4(accessIpV4);
		String accessIpV6 = body.getAccessIPv6();
		validateAccessIpv6(accessIpV6);
		String name = body.getName();
		validateServerName(name);
		String diskConfig = body.getDiskConfig();
		Map<String, String> metadata = body.getMetadata();

		try {
			computeApi.rebuild(context, instance, imageHref, password, accessIpV4, accessIpV6, name, metadata, diskConfig);
		} catch (InstanceNotFoundException e) {
			String msg = "Instance could not be found";
			throw new HTTPNotFoundException(msg);
		} catch (ImageNotFoundException e) {
			String msg = "Cannot find image for rebuild";
			throw new HTTPBadRequestException(msg);
		}

		instance = getServer(context, requestContext, serverId);
		ServerTemplate view = viewBuilder.show(requestContext, instance);
		if (config.getOpt("enable_instance_password").asBoolean()) {
			view.getServer().setAdminPass(password);
		}

		return view;
	}

	@Override
	public Response revertResize(String serverId, ContainerRequestContext requestContext, ServerAction.RevertResize body)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		computeApi.revertResize(context, instance);
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response confirmResize(String serverId, ContainerRequestContext requestContext, ServerAction.ConfirmResize body)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		computeApi.confirmResize(context, instance);
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response changePassword(String serverId, ContainerRequestContext requestContext, ServerAction.ChangePassword body)
			throws Exception {
		if (Strings.isNullOrEmpty(body.getAdminPass())) {
			String msg = "No adminPass was specified";
			logger.error(msg);
			throw new HTTPBadRequestException(msg);
		}

		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		computeApi.setAdminPassword(context, instance, body.getAdminPass());
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response createImage(String serverId, ContainerRequestContext requestContext, ServerAction.CreateImage body)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		String imageName = body.getName();
		if (Strings.isNullOrEmpty(imageName)) {
			String msg = "createImage entity requires name attribute";
			throw new HTTPBadRequestException(msg);
		}
		Server instance = getServer(context, requestContext, serverId);
		try {
			computeApi.snapshot(context, instance, imageName, body.getMetadata());
		} catch (InvalidException e) {
			throw new HTTPBadRequestException(e.getMessage());
		}
		return Response.status(Status.ACCEPTED).build();
	}

	private Response resize(ContainerRequestContext requestContext, String serverId, String flavorId, String autoDiskConfig)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		try {
			computeApi.resize(context, instance, flavorId, autoDiskConfig);
		} catch (FlavorNotFoundException e) {
			String msg = "Unable to locate requested flavor.";
			throw new HTTPBadRequestException(msg);
		} catch (CannotResizeToSameFlavorException e) {
			String msg = "Resize requires a flavor change.";
			throw new HTTPBadRequestException(msg);
		} catch (ImageNotFoundException e) {
			String msg = "Image that the instance was started with could not be found.";
			throw new HTTPBadRequestException(msg);
		} catch (InvalidException e) {
			String msg = "Invalid instance image.";
			throw new HTTPBadRequestException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	private Server getServer(OpenstackRequestContext context, ContainerRequestContext requestContext, String serverId)
			throws Exception {
		try {
			Server instance = computeApi.get(context, serverId, null);
			return instance;
		} catch (NotFoundException e) {
			String msg = "Instance could not be found";
			throw new HTTPNotFoundException(msg);
		}
	}

	@Override
	public ServerTemplate update(ContainerRequestContext requestContext, String serverId, ServerForCreate server)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		String name = server.getName();
		String ipv4 = server.getAccessIPv4();
		String ipv6 = server.getAccessIPv6();

		try {
			Server instance = computeApi.get(context, serverId, null);
			// Policy.enforce(context, "compute:update", instance, true, null);
			instance = computeApi.update(context, serverId, name, ipv4, ipv6);
			return viewBuilder.show(requestContext, instance);
		} catch (NotFoundException e) {
			String msg = "Instance could not be found";
			throw new HTTPNotFoundException(msg);
		}
	}

	@Override
	public Response pause(String serverId, ContainerRequestContext requestContext, Pause value) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		// authorize(context, "pause");
		Server instance = getServer(context, requestContext, serverId);
		try {
			computeApi.pause(context, instance);
		} catch (InstanceNotFoundException e) {
			String msg = "Server not found";
			throw new HTTPNotFoundException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response unpause(String serverId, ContainerRequestContext requestContext, Unpause value) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		// authorize(context, "unpause");
		Server instance = getServer(context, requestContext, serverId);
		try {
			computeApi.unpause(context, instance);
		} catch (InstanceNotFoundException e) {
			String msg = "Server not found";
			throw new HTTPNotFoundException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response suspend(String serverId, ContainerRequestContext requestContext, Suspend value) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		// authorize(context, "suspend");
		Server instance = getServer(context, requestContext, serverId);
		try {
			computeApi.suspend(context, instance);
		} catch (InstanceNotFoundException e) {
			String msg = "Server not found";
			throw new HTTPNotFoundException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response resume(String serverId, ContainerRequestContext requestContext, Resume value) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		// authorize(context, "resume");
		Server instance = getServer(context, requestContext, serverId);
		try {
			computeApi.resume(context, instance);
		} catch (InstanceNotFoundException e) {
			String msg = "Server not found";
			throw new HTTPNotFoundException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response start(String serverId, ContainerRequestContext requestContext, Start value) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		// checkComputePolicy(context, "start", instance, null);
		try {
			computeApi.start(context, instance);
		} catch (InstanceNotFoundException e) {
			String msg = "Server not found";
			throw new HTTPNotFoundException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	@Override
	public Response stop(String serverId, ContainerRequestContext requestContext, Stop value) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Server instance = getServer(context, requestContext, serverId);
		// checkComputePolicy(context, "stop", instance, null);
		try {
			computeApi.stop(context, instance);
		} catch (InstanceNotFoundException e) {
			String msg = "Server not found";
			throw new HTTPNotFoundException(msg);
		}
		return Response.status(Status.ACCEPTED).build();
	}

	// private void checkComputePolicy(OpenstackRequestContext context, String
	// action, Target target, String scope)
	// throws Exception {
	// if (Strings.isNullOrEmpty(scope)) {
	// scope = "compute";
	// }
	// action = String.format("%s:%s", scope, action);
	// Policy.enforce(context, action, target, true, null);
	// }

	// private void authorize(OpenstackRequestContext context, String
	// actionName) throws Exception {
	// String action = String.format("admin_actions:%s", actionName);
	// Extensions.extensionAuthorizer("compute", action).authorize(context,
	// null, null);
	// }

	private Entry<List<String>, List<String>> taskAndVmStateFromStatus(List<String> statuses) {
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

}
