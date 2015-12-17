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
package com.infinities.nova.api.openstack.compute.images.api;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.Tag;

import com.google.common.base.Preconditions;
import com.infinities.nova.api.Context;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedMachineImageSupport;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinImageMetadataApi implements ImageMetadataApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinImageMetadataApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	@Override
	public void updateAll(NovaRequestContext context, String imageId, Map<String, String> metadata) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		Tag[] tags = new Tag[metadata.size()];
		int i = 0;
		for (Entry<String, String> meta : metadata.entrySet()) {
			tags[i++] = new Tag(meta.getKey(), meta.getValue());
		}
		getSupport(context.getProjectId()).setTags(imageId, tags);
	}

	@Override
	public void create(NovaRequestContext context, String imageId, Map<String, String> metadata) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		Tag[] tags = new Tag[metadata.size()];
		int i = 0;
		for (Entry<String, String> meta : metadata.entrySet()) {
			tags[i++] = new Tag(meta.getKey(), meta.getValue());
		}
		getSupport(context.getProjectId()).updateTags(imageId, tags);
	}

	@Override
	public void update(NovaRequestContext context, String imageId, String key, Map<String, String> meta) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).updateTags(imageId, new Tag(key, meta.get(key)));
	}

	@Override
	public void delete(NovaRequestContext context, String imageId, String key) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).removeTags(imageId, new Tag(key, ""));
	}

	private CachedMachineImageSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasImageSupport()) {
				return provider.getComputeServices().getImageSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}
}
