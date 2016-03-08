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
package com.infinities.nova.images.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.compute.ImageFilterOptions;
import org.dasein.cloud.compute.MachineImage;

import com.google.common.base.Preconditions;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.AbstractPaginableController.PaginationParams;
import com.infinities.nova.images.controller.ImagesFilter;
import com.infinities.nova.images.model.Image;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedMachineImageSupport;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinImagesApi implements ImagesApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinImagesApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	@Override
	public Image get(OpenstackRequestContext context, String imageId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<MachineImage> result = getSupport(context.getProjectId()).getImage(imageId);
		MachineImage machineImage = result.get();
		Image image = toImage(machineImage);
		return image;
	}

	private Image toImage(MachineImage machineImage) {
		Image output = new Image();
		Calendar created = Calendar.getInstance();
		created.setTimeInMillis(machineImage.getCreationTimestamp());
		output.setCreated(created);
		output.setId(machineImage.getProviderMachineImageId());
		output.setMetadata(machineImage.getProviderMetadata());
		output.setMinDisk((int) machineImage.getMinimumDiskSizeGb());
		// output.setMinRam();
		output.setName(machineImage.getName());
		// output.setProgress();
		// Image.Server server = new Image.Server();
		// server.setId(input.getServer());
		// output.setServer(server);
		// output.setSize(input.getSize());
		output.setStatus(machineImage.getCurrentState().name());
		// output.setUpdated();
		output.setMetadata(machineImage.getTags());
		return output;
	}

	@Override
	public List<Image> getAll(OpenstackRequestContext context, ImagesFilter filters, PaginationParams pageParams)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<MachineImage>> result =
				getSupport(context.getProjectId()).listImages(ImageFilterOptions.getInstance());
		Iterable<MachineImage> iterable = result.get();
		Iterator<MachineImage> iterator = iterable.iterator();

		List<Image> images = new ArrayList<Image>();
		while (iterator.hasNext()) {
			MachineImage machineImage = iterator.next();
			images.add(toImage(machineImage));
		}

		return images;
	}

	@Override
	public void delete(OpenstackRequestContext context, String imageId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).remove(imageId);
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
