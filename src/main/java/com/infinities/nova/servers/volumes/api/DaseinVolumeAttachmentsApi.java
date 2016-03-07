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
package com.infinities.nova.servers.volumes.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.compute.Volume;
import org.dasein.cloud.compute.VolumeFilterOptions;

import com.google.common.base.Preconditions;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.exception.VolumeNotFoundException;
import com.infinities.nova.servers.volumes.model.VolumeAttachment;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedVolumeSupport;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinVolumeAttachmentsApi implements VolumeAttachmentsApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinVolumeAttachmentsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.volumes.api.VolumeAttachmentsApi#
	 * getVolumeAttachments(com.infinities.nova.OpenstackRequestContext,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<VolumeAttachment> getVolumeAttachments(OpenstackRequestContext context, String projectId, String serverId)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<Volume>> result =
				this.getSupport(context.getProjectId()).listVolumes(VolumeFilterOptions.getInstance().attachedTo(serverId));
		Iterable<Volume> iterable = result.get();
		List<VolumeAttachment> volumeAttachments = new ArrayList<VolumeAttachment>();
		Iterator<Volume> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			Volume volume = iterator.next();
			volumeAttachments.add(toVolumeAttachments(volume));
		}
		return volumeAttachments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.volumes.api.VolumeAttachmentsApi#
	 * getVolumeAttachment(com.infinities.nova.OpenstackRequestContext,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public VolumeAttachment getVolumeAttachment(OpenstackRequestContext context, String projectId, String serverId,
			String volumeId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Volume> result = this.getSupport(context.getProjectId()).getVolume(volumeId);
		Volume volume = result.get();
		if (volume == null) {
			throw new VolumeNotFoundException(null, volumeId);
		}
		return toVolumeAttachments(volume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.volumes.api.VolumeAttachmentsApi#detach(com
	 * .infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void detach(OpenstackRequestContext context, String projectId, String serverId, String volumeId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).detach(volumeId);
		result.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.volumes.api.VolumeAttachmentsApi#attach(com
	 * .infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public VolumeAttachment attach(OpenstackRequestContext context, String projectId, String serverId, String volumeId,
			String device) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).attach(volumeId, serverId, device);
		result.get();
		return getVolumeAttachment(context, projectId, serverId, volumeId);
	}

	/**
	 * @param volume
	 * @return
	 */
	private VolumeAttachment toVolumeAttachments(Volume volume) {
		VolumeAttachment attachment = new VolumeAttachment();
		attachment.setDevice(volume.getDeviceId());
		attachment.setId(volume.getProviderVolumeId());
		attachment.setServerId(volume.getProviderVirtualMachineId());
		attachment.setVolumeId(volume.getProviderVolumeId());
		return attachment;
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

}
