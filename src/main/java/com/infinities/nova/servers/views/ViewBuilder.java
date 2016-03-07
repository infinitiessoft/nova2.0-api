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
package com.infinities.nova.servers.views;

import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.model.Link;
import com.infinities.api.openstack.commons.views.AbstractViewBuilder;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.response.model.Server;
import com.infinities.nova.response.model.Server.Addresses;
import com.infinities.nova.response.model.Server.Fault;
import com.infinities.nova.servers.api.TaskStates;
import com.infinities.nova.servers.api.VmStates;
import com.infinities.nova.servers.ips.controller.NetworkUtils;
import com.infinities.nova.servers.model.CreatedServer;
import com.infinities.nova.servers.model.CreatedServerTemplate;
import com.infinities.nova.servers.model.MinimalServer;
import com.infinities.nova.servers.model.MinimalServerTemplate;
import com.infinities.nova.servers.model.MinimalServersTemplate;
import com.infinities.nova.servers.model.ServerTemplate;
import com.infinities.nova.servers.model.ServersTemplate;
import com.infinities.nova.util.URLUtils;

public class ViewBuilder extends AbstractViewBuilder {

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

	private static final Logger logger = LoggerFactory.getLogger(ViewBuilder.class);
	private int osapiMaxLimit;
	private static final Map<String, Map<String, String>> STATE_MAP;


	public ViewBuilder(String osapiComputeLinkPrefix, int osapiMaxLimit) {
		super(osapiComputeLinkPrefix);
		addressBuilder = new com.infinities.nova.servers.ips.views.ViewBuilder(osapiComputeLinkPrefix);
		this.osapiMaxLimit = osapiMaxLimit;
	}


	// private final static Logger logger =
	// LoggerFactory.getLogger(ViewBuilder.class);
	private final static String COLLECTION_NAME = "servers";
	private final static Set<String> PROGRESS_STATUSES;
	private final static Map<String, String> FAULT_STATUSES;
	private final com.infinities.nova.servers.ips.views.ViewBuilder addressBuilder;
	// private final com.infinities.nova.views.flavors.ViewBuilder flavorBuilder
	// = new com.infinities.nova.views.flavors.ViewBuilder();
	// private final com.infinities.nova.views.images.ViewBuilder imageBuilder =
	// new com.infinities.nova.views.images.ViewBuilder();

	static {
		Set<String> set = new HashSet<String>();
		set.add("ACTIVE");
		set.add("BUILD");
		set.add("REBUILD");
		set.add("RESIZE");
		set.add("VERIFY_RESIZE");
		PROGRESS_STATUSES = Collections.unmodifiableSet(set);

		Map<String, String> map = new HashMap<String, String>();
		map.put("ERROR", "DELETED");
		FAULT_STATUSES = Collections.unmodifiableMap(map);
	}


	public ServersTemplate detail(ContainerRequestContext requestContext, List<Instance> instanceList)
			throws URISyntaxException, NoSuchAlgorithmException, UnknownHostException, CloneNotSupportedException {
		String collName = ViewBuilder.COLLECTION_NAME + "/detail";
		List<Server> serverList = new ArrayList<Server>();
		for (Instance instance : instanceList) {
			serverList.add(show(requestContext, instance).getServer());
		}

		ServersTemplate servers = new ServersTemplate();
		servers.setList(serverList);
		List<Link> serversLinks = this.getCollectionLinks(requestContext, instanceList, collName);

		if (serversLinks != null) {
			servers.setLinks(serversLinks);
		}

		return servers;
	}

	public MinimalServersTemplate index(ContainerRequestContext requestContext, List<Instance> instanceList)
			throws URISyntaxException {
		String collName = ViewBuilder.COLLECTION_NAME;
		List<MinimalServer> serverList = new ArrayList<MinimalServer>();
		for (Instance instance : instanceList) {
			serverList.add(basic(requestContext, instance).getServer());
		}

		MinimalServersTemplate servers = new MinimalServersTemplate();
		servers.setList(serverList);
		List<Link> serversLinks = this.getCollectionLinks(requestContext, instanceList, collName);

		if (serversLinks != null) {
			servers.setLinks(serversLinks);
		}

		return servers;
	}

	private MinimalServerTemplate basic(ContainerRequestContext requestContext, Instance instance) throws URISyntaxException {
		MinimalServer server = new MinimalServer();
		server.setId(instance.getInstanceId());
		server.setName(instance.getName());
		server.setLinks(getLinks(requestContext, instance.getInstanceId(), COLLECTION_NAME));
		MinimalServerTemplate template = new MinimalServerTemplate(server);
		return template;
	}

	public ServerTemplate show(ContainerRequestContext requestContext, Instance instance) throws NoSuchAlgorithmException,
			URISyntaxException, UnknownHostException, CloneNotSupportedException {
		String ipV4 = instance.getAccessIpV4();
		String ipV6 = instance.getAccessIpV6();
		Server server = new Server();
		server.setId(instance.getInstanceId());
		server.setName(instance.getName());
		server.setStatus(getVmStatus(instance));
		String projectId = instance.getTenantId();
		if (Strings.isNullOrEmpty(projectId)) {
			projectId = "";
		}
		server.setTenantId(projectId);
		String userId = instance.getUserId();
		if (Strings.isNullOrEmpty(userId)) {
			userId = "";
		}
		server.setUserId(instance.getUserId());
		server.setMetadata(getMetadata(instance));
		String hostId = getHostId(instance);
		if (Strings.isNullOrEmpty(hostId)) {
			hostId = "";
		}
		server.setHostId(hostId);
		server.setImage(getImage(requestContext, instance));
		server.setFlavor(getFlavor(requestContext, instance));
		server.setCreated(instance.getCreatedAt());
		server.setUpdated(instance.getUpdatedAt());
		server.setAddresses(getAddress(requestContext, instance));
		if (Strings.isNullOrEmpty(ipV4)) {
			ipV4 = "";
		}
		server.setAccessIPv4(ipV4);
		if (Strings.isNullOrEmpty(ipV6)) {
			ipV6 = "";
		}
		server.setAccessIPv6(ipV6);
		server.setLinks(getLinks(requestContext, instance.getInstanceId(), COLLECTION_NAME));

		if (FAULT_STATUSES.containsKey(server.getStatus())) {
			Fault instFault = getFault(requestContext, instance);
			if (instFault != null) {
				server.setFault(instFault);
			}
		}

		if (PROGRESS_STATUSES.contains(server.getStatus())) {
			Integer progress = instance.getProgress();
			if (progress == null) {
				progress = 0;
			}
			server.setProgress(progress);
		}
		return new ServerTemplate(server);
	}

	private Fault getFault(ContainerRequestContext requestContext, Instance instance) {
		com.infinities.nova.db.model.Fault fault = instance.getFault();

		if (fault == null) {
			return null;
		}

		Fault f = new Fault();
		f.setCode(fault.getCode());
		f.setCreated(fault.getCreated());
		f.setMessage(fault.getMessage());

		if (!Strings.isNullOrEmpty(fault.getDetails())) {
			boolean isAdmin = false;
			OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
			if (context != null) {
				isAdmin = context.getIsAdmin();
			}

			if (isAdmin || fault.getCode() != 500) {
				f.setDetails(fault.getDetails());
			}
		}

		return f;
	}

	private Addresses getAddress(ContainerRequestContext requestContext, Instance instance) throws UnknownHostException,
			CloneNotSupportedException {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Map<String, NetworkUtils.Network> networks = NetworkUtils.getNetworksForInstance(context, instance);
		return addressBuilder.index(networks);
	}

	private com.infinities.nova.response.model.Server.Flavor getFlavor(ContainerRequestContext requestContext,
			Instance instance) throws URISyntaxException {
		com.infinities.nova.response.model.Server.Flavor flavor = new com.infinities.nova.response.model.Server.Flavor();
		// InstanceType instanceType = Common.extractFlavor(instance, null);
		// if (instanceType == null) {
		// logger.warn("Instance has had its instance_type removed from the DB");
		// return flavor;
		// }
		String flavorId = instance.getFlavorId();
		String flavorBookmark = getBookmarkLink(requestContext, flavorId, "flavors");
		flavor.setId(flavorId);
		List<Link> links = new ArrayList<Link>();
		Link link = new Link();
		link.setRel("bookmark");
		link.setHref(flavorBookmark);
		links.add(link);
		flavor.setLinks(links);

		return flavor;
	}

	private com.infinities.nova.response.model.Server.Image getImage(ContainerRequestContext requestContext,
			Instance instance) throws URISyntaxException {
		String imageRef = instance.getImageId();
		com.infinities.nova.response.model.Server.Image image = new com.infinities.nova.response.model.Server.Image();
		if (!Strings.isNullOrEmpty(imageRef)) {
			String imageId = URLUtils.getIdFromHref(imageRef);
			String bookmark = getBookmarkLink(requestContext, imageId, "images");
			image.setId(imageId);
			List<Link> links = new ArrayList<Link>();
			Link link = new Link();
			link.setRel("bookmark");
			link.setHref(bookmark);
			links.add(link);
			image.setLinks(links);
		}
		return image;
	}

	private String getHostId(Instance instance) throws NoSuchAlgorithmException {
		String host = instance.getHost();
		String project = instance.getTenantId();
		if (!Strings.isNullOrEmpty(host)) {
			Security.addProvider(new BouncyCastleProvider());
			MessageDigest md = MessageDigest.getInstance("SHA-224");
			String word = project + host;
			byte[] digest = md.digest(word.getBytes());
			return new String(Hex.encode(digest));
		}
		return null;
	}

	private Map<String, String> getMetadata(Instance instance) {
		return instance.getMetadata();
	}

	private String getVmStatus(Instance instance) {
		if (instance.getDeleted() == 1) {
			return "DELETED";
		}
		return statusFromState(instance.getVmState(), instance.getTaskState());
	}

	private String statusFromState(String vmState, String taskState) {
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

	private List<Link> getCollectionLinks(ContainerRequestContext requestContext, List<Instance> instances,
			String collectionName) throws URISyntaxException {
		List<Link> links = new ArrayList<Link>();
		String limitQP = requestContext.getUriInfo().getQueryParameters().getFirst("limit");
		int limitQ = osapiMaxLimit;
		int maxItems = limitQ;

		if (!Strings.isNullOrEmpty(limitQP)) {
			int limit = Integer.parseInt(limitQP);
			maxItems = Math.min(maxItems, limit);
		}

		if (maxItems == instances.size()) {
			int item = instances.size() - 1;
			if (item >= 0) {
				Instance lastItem = instances.get(item);
				String lastItemId = lastItem.getInstanceId();
				Link link = new Link();
				link.setRel("next");
				link.setHref(getNextLink(requestContext, lastItemId, collectionName));
				links.add(link);
			}
		}

		if (links.isEmpty()) {
			return null;
		}

		return links;
	}

	public CreatedServerTemplate create(ContainerRequestContext requestContext, Instance instance) throws URISyntaxException {
		CreatedServer server = new CreatedServer();
		CreatedServerTemplate template = new CreatedServerTemplate(server);
		server.setId(instance.getInstanceId());
		server.setLinks(getLinks(requestContext, instance.getInstanceId(), COLLECTION_NAME));
		return template;
	}
}
