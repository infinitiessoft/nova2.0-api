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
package com.infinities.nova.servers.metadata.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.exception.http.HTTPBadRequestException;
import com.infinities.api.openstack.commons.exception.http.HTTPNotFoundException;
import com.infinities.nova.common.model.MetaItemTemplate;
import com.infinities.nova.common.model.MetadataTemplate;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.exception.InstanceNotFoundException;
import com.infinities.nova.servers.api.ComputeApi;

public class ServerMetadataControllerImpl implements ServerMetadataController {

	private final ComputeApi computeApi;


	public ServerMetadataControllerImpl(ComputeApi computeApi) {
		this.computeApi = computeApi;
	}

	@Override
	public MetadataTemplate index(ContainerRequestContext requestContext, String serverId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Map<String, String> metadata = getMetadata(context, serverId);
		MetadataTemplate wrapper = new MetadataTemplate();
		wrapper.setMetadata(metadata);
		return wrapper;
	}

	private Map<String, String> getMetadata(OpenstackRequestContext context, String serverId) throws Exception {
		try {
			Instance server = computeApi.get(context, serverId, null);
			Map<String, String> meta = computeApi.getInstanceMetadata(context, server);

			Map<String, String> metaDict = new HashMap<String, String>();
			for (Entry<String, String> entry : meta.entrySet()) {
				metaDict.put(entry.getKey(), entry.getValue());
			}

			return metaDict;
		} catch (InstanceNotFoundException e) {
			String msg = "Server does not exist";
			throw new HTTPNotFoundException(msg);
		}
	}

	@Override
	public MetadataTemplate create(ContainerRequestContext requestContext, String serverId, MetadataTemplate wrapper)
			throws Exception {
		Map<String, String> metadata = wrapper.getMetadata();
		if (metadata == null) {
			String msg = "Malformed request body";
			throw new HTTPBadRequestException(msg);
		}

		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Map<String, String> newMetadata = updateInstanceMetadata(context, serverId, metadata, false);
		MetadataTemplate wrapperRet = new MetadataTemplate();
		wrapperRet.setMetadata(newMetadata);
		return wrapperRet;
	}

	// delete = false
	private Map<String, String> updateInstanceMetadata(OpenstackRequestContext context, String serverId,
			Map<String, String> metadata, boolean delete) throws Exception {
		Instance server = null;
		server = computeApi.get(context, serverId, null);
		return computeApi.updateInstanceMetadata(context, server, metadata, delete);
	}

	@Override
	public MetaItemTemplate update(ContainerRequestContext requestContext, String serverId, String key, MetaItemTemplate meta)
			throws Exception {
		Map<String, String> metaItem = meta.getMeta();
		if (metaItem == null || metaItem.isEmpty()) {
			String msg = "Malformed request body";
			throw new HTTPBadRequestException(msg);
		}

		if (!metaItem.containsKey(key)) {
			String msg = "Request body and URI mismatch";
			throw new HTTPBadRequestException(msg);
		}

		if (metaItem.size() > 1) {
			String msg = "Request body contains too many items";
			throw new HTTPBadRequestException(msg);
		}
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		updateInstanceMetadata(context, serverId, metaItem, false);

		return meta;
	}

	@Override
	public MetadataTemplate updateAll(ContainerRequestContext requestContext, String serverId, MetadataTemplate wrapper)
			throws Exception {
		Map<String, String> metadata = wrapper.getMetadata();
		if (metadata == null) {
			String msg = "Malformed request body";
			throw new HTTPBadRequestException(msg);
		}
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Map<String, String> newMetadata = updateInstanceMetadata(context, serverId, metadata, true);
		MetadataTemplate wrapperRet = new MetadataTemplate();
		wrapperRet.setMetadata(newMetadata);
		return wrapperRet;
	}

	@Override
	public MetaItemTemplate show(ContainerRequestContext requestContext, String serverId, String key) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Map<String, String> data = getMetadata(context, serverId);
		MetaItemTemplate wrapper = new MetaItemTemplate();
		Map<String, String> meta = new HashMap<String, String>();
		if (data.containsKey(key)) {
			meta.put(key, data.get(key));
		} else {
			String msg = "Metadata item was not found";
			throw new HTTPNotFoundException(msg);
		}
		wrapper.setMeta(meta);

		return wrapper;
	}

	@Override
	public void delete(ContainerRequestContext requestContext, String serverId, String key) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		Map<String, String> metadata = getMetadata(context, serverId);
		if (!metadata.containsKey(key)) {
			String msg = "Metadata item was not found";
			throw new HTTPNotFoundException(msg);
		}

		Instance server = computeApi.get(context, serverId, null);
		computeApi.deleteInstanceMetadata(context, server, key);
	}

}
