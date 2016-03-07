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
package com.infinities.nova.volumes.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.compute.VolumeCreateOptions;
import org.dasein.cloud.compute.VolumeProduct;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.exception.AvailabilityZoneNotFoundException;
import com.infinities.nova.exception.VolumeNotFoundException;
import com.infinities.nova.servers.volumes.model.VolumeAttachment;
import com.infinities.nova.volumes.model.Volume;
import com.infinities.nova.volumes.model.VolumeForCreateTemplate;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.AsyncDataCenterServices;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedVolumeSupport;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinVolumesApi implements VolumesApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinVolumesApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	private CachedVolumeSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasVolumeSupport()) {
				return provider.getComputeServices().getVolumeSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.api.VolumesApi#getVolumes(com.infinities.
	 * nova.OpenstackRequestContext, java.lang.String)
	 */
	@Override
	public List<Volume> getVolumes(OpenstackRequestContext context, String projectId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<org.dasein.cloud.compute.Volume>> result =
				this.getSupport(context.getProjectId()).listVolumes();

		AsyncResult<Iterable<VolumeProduct>> types = this.getSupport(context.getProjectId()).listVolumeProducts();

		Iterable<org.dasein.cloud.compute.Volume> iterable = result.get();
		List<Volume> volumes = new ArrayList<Volume>();
		Iterator<org.dasein.cloud.compute.Volume> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			org.dasein.cloud.compute.Volume volume = iterator.next();
			volumes.add(toVolume(volume, types.get()));
		}
		return volumes;
	}

	/**
	 * @param volume
	 * @param iterable
	 * @return
	 */
	private Volume toVolume(org.dasein.cloud.compute.Volume volume, Iterable<VolumeProduct> iterable) {
		final Volume ret = new Volume();
		ret.setAvailabilityZone(volume.getProviderDataCenterId());
		Calendar createdAt = Calendar.getInstance();
		createdAt.setTimeInMillis(volume.getCreationTimestamp());
		ret.setCreatedAt(createdAt);
		ret.setDescription(volume.getDescription());
		ret.setId(volume.getProviderVolumeId());
		ret.setMetadata(volume.getTags());
		ret.setName(volume.getName());
		ret.setSize(volume.getSizeInGigabytes());
		ret.setSnapshotId(volume.getProviderSnapshotId());
		ret.setStatus(volume.getCurrentState().name());
		ret.setVolumeType(volume.getProviderProductId());
		try {
			VolumeProduct product = Iterables.find(iterable, new Predicate<VolumeProduct>() {

				@Override
				public boolean apply(VolumeProduct input) {
					return input.getProviderProductId().equals(ret.getVolumeType());
				}
			});
			ret.setVolumeType(product.getName());
		} catch (NoSuchElementException e) {
			ret.setVolumeType(null);
			// ignore
		}

		List<VolumeAttachment> attachments = new ArrayList<VolumeAttachment>();
		if (!Strings.isNullOrEmpty(volume.getProviderVirtualMachineId()) && !Strings.isNullOrEmpty(volume.getDeviceId())) {
			VolumeAttachment attachment = new VolumeAttachment();
			attachment.setId(volume.getProviderVolumeId());
			attachment.setDevice(volume.getDeviceId());
			attachment.setVolumeId(volume.getProviderVolumeId());
			attachment.setServerId(volume.getProviderVirtualMachineId());
			attachments.add(attachment);
		}
		ret.setAttachments(attachments);

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.api.VolumesApi#getVolume(com.infinities.nova
	 * .OpenstackRequestContext, java.lang.String, java.lang.String)
	 */
	@Override
	public Volume getVolume(OpenstackRequestContext context, String projectId, String volumeId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<org.dasein.cloud.compute.Volume> result = this.getSupport(context.getProjectId()).getVolume(volumeId);
		AsyncResult<Iterable<VolumeProduct>> types = this.getSupport(context.getProjectId()).listVolumeProducts();
		org.dasein.cloud.compute.Volume volume = result.get();
		if (volume == null) {
			throw new VolumeNotFoundException(null, volumeId);
		}
		return toVolume(volume, types.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.api.VolumesApi#createVolume(com.infinities
	 * .nova.OpenstackRequestContext, java.lang.String,
	 * com.infinities.nova.volumes.model.VolumeForCreateTemplate)
	 */
	@Override
	public Volume createVolume(OpenstackRequestContext context, String projectId,
			final VolumeForCreateTemplate volumeForCreateTemplate) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		Storage<Gigabyte> size = new Storage<Gigabyte>(volumeForCreateTemplate.getVolume().getSize(), Storage.GIGABYTE);
		String name = volumeForCreateTemplate.getVolume().getName();
		String description = volumeForCreateTemplate.getVolume().getDescription();
		VolumeCreateOptions options = VolumeCreateOptions.getInstance(size, name, description);

		if (!Strings.isNullOrEmpty(volumeForCreateTemplate.getVolume().getAvailabilityZone())) {
			AsyncResult<Iterable<DataCenter>> result =
					getServices(context.getProjectId()).listDataCenters(getRegionId(context.getProjectId()));
			try {
				DataCenter dc = Iterables.find(result.get(), new Predicate<DataCenter>() {

					@Override
					public boolean apply(DataCenter input) {
						return input.getProviderDataCenterId().equals(
								volumeForCreateTemplate.getVolume().getAvailabilityZone());
					}
				});
				options.setDataCenterId(dc.getProviderDataCenterId());
			} catch (NoSuchElementException e) {
				throw new AvailabilityZoneNotFoundException(null, volumeForCreateTemplate.getVolume().getAvailabilityZone());
			}
		}

		options.setVolumeProductId(volumeForCreateTemplate.getVolume().getVolumeType());
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : volumeForCreateTemplate.getVolume().getMetadata().keySet()) {
			map.put(key, volumeForCreateTemplate.getVolume().getMetadata().get(key));
		}
		options.setMetaData(map);
		AsyncResult<String> ret = this.getSupport(context.getProjectId()).createVolume(options);
		return getVolume(context, projectId, ret.get());
	}

	private AsyncDataCenterServices getServices(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);
		return provider.getDataCenterServices();
	}

	private String getRegionId(String id) {
		CachedServiceProvider provider = configurationHome.findById(id);
		Preconditions.checkArgument(provider != null, "invalid project id:" + id);
		return provider.getContext().getRegionId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.api.VolumesApi#deleteVolume(com.infinities
	 * .nova.OpenstackRequestContext, java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteVolume(OpenstackRequestContext context, String projectId, String volumeId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).remove(volumeId);
		result.get();
	}
}
