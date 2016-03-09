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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.base.Strings;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.model.Link;
import com.infinities.api.openstack.commons.views.AbstractViewBuilder;
import com.infinities.nova.servers.model.CreatedServer;
import com.infinities.nova.servers.model.CreatedServerTemplate;
import com.infinities.nova.servers.model.MinimalServer;
import com.infinities.nova.servers.model.MinimalServerTemplate;
import com.infinities.nova.servers.model.MinimalServersTemplate;
import com.infinities.nova.servers.model.Server;
import com.infinities.nova.servers.model.Server.Fault;
import com.infinities.nova.servers.model.ServerTemplate;
import com.infinities.nova.servers.model.ServersTemplate;
import com.infinities.nova.util.URLUtils;

public class ViewBuilder extends AbstractViewBuilder {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ViewBuilder.class);
	private int osapiMaxLimit;


	public ViewBuilder(String osapiComputeLinkPrefix, int osapiMaxLimit) {
		super(osapiComputeLinkPrefix);
		this.osapiMaxLimit = osapiMaxLimit;
	}


	private final static String COLLECTION_NAME = "servers";
	private final static Set<String> PROGRESS_STATUSES;
	private final static Map<String, String> FAULT_STATUSES;

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


	public ServersTemplate detail(ContainerRequestContext requestContext, List<Server> instanceList)
			throws URISyntaxException, NoSuchAlgorithmException, UnknownHostException, CloneNotSupportedException {
		String collName = ViewBuilder.COLLECTION_NAME + "/detail";
		List<Server> serverList = new ArrayList<Server>();
		for (Server instance : instanceList) {
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

	public MinimalServersTemplate index(ContainerRequestContext requestContext, List<Server> instanceList)
			throws URISyntaxException {
		String collName = ViewBuilder.COLLECTION_NAME;
		List<MinimalServer> serverList = new ArrayList<MinimalServer>();
		for (Server instance : instanceList) {
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

	private MinimalServerTemplate basic(ContainerRequestContext requestContext, Server instance) throws URISyntaxException {
		MinimalServer server = new MinimalServer();
		server.setId(instance.getId());
		server.setName(instance.getName());
		server.setLinks(getLinks(requestContext, instance.getId(), COLLECTION_NAME));
		MinimalServerTemplate template = new MinimalServerTemplate(server);
		return template;
	}

	public ServerTemplate show(ContainerRequestContext requestContext, Server instance) throws NoSuchAlgorithmException,
			URISyntaxException, UnknownHostException, CloneNotSupportedException {
		String ipV4 = instance.getAccessIPv4();
		String ipV6 = instance.getAccessIPv6();
		Server server = new Server();
		server.setId(instance.getId());
		server.setName(instance.getName());
		server.setStatus(instance.getStatus());
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
		server.setMetadata(instance.getMetadata());
		String hostId = instance.getHostId();
		if (Strings.isNullOrEmpty(hostId)) {
			hostId = "";
		}
		server.setHostId(hostId);
		server.setImage(getImage(requestContext, instance));
		server.setFlavor(getFlavor(requestContext, instance));
		server.setCreated(instance.getCreated());
		server.setUpdated(instance.getUpdated());
		server.setAddresses(instance.getAddresses());
		if (Strings.isNullOrEmpty(ipV4)) {
			ipV4 = "";
		}
		server.setAccessIPv4(ipV4);
		if (Strings.isNullOrEmpty(ipV6)) {
			ipV6 = "";
		}
		server.setAccessIPv6(ipV6);
		server.setLinks(getLinks(requestContext, instance.getId(), COLLECTION_NAME));

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

	private Fault getFault(ContainerRequestContext requestContext, Server server) {
		Fault fault = server.getFault();

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

	private com.infinities.nova.servers.model.Server.Flavor getFlavor(ContainerRequestContext requestContext, Server instance)
			throws URISyntaxException {
		com.infinities.nova.servers.model.Server.Flavor flavor = new com.infinities.nova.servers.model.Server.Flavor();
		// InstanceType instanceType = Common.extractFlavor(instance, null);
		// if (instanceType == null) {
		// logger.warn("Instance has had its instance_type removed from the DB");
		// return flavor;
		// }
		String flavorId = instance.getFlavor().getId();
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

	private com.infinities.nova.servers.model.Server.Image getImage(ContainerRequestContext requestContext, Server instance)
			throws URISyntaxException {
		String imageRef = instance.getImage().getId();
		com.infinities.nova.servers.model.Server.Image image = new com.infinities.nova.servers.model.Server.Image();
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

	// private String getHostId(Server instance) throws NoSuchAlgorithmException
	// {
	// String host = instance.getHost();
	// String project = instance.getTenantId();
	// if (!Strings.isNullOrEmpty(host)) {
	// Security.addProvider(new BouncyCastleProvider());
	// MessageDigest md = MessageDigest.getInstance("SHA-224");
	// String word = project + host;
	// byte[] digest = md.digest(word.getBytes());
	// return new String(Hex.encode(digest));
	// }
	// return null;
	// }

	private List<Link> getCollectionLinks(ContainerRequestContext requestContext, List<Server> instances,
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
				Server lastItem = instances.get(item);
				String lastItemId = lastItem.getId();
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

	public CreatedServerTemplate create(ContainerRequestContext requestContext, Server instance) throws URISyntaxException {
		CreatedServer server = new CreatedServer();
		CreatedServerTemplate template = new CreatedServerTemplate(server);
		server.setId(instance.getId());
		server.setLinks(getLinks(requestContext, instance.getId(), COLLECTION_NAME));
		return template;
	}
}
