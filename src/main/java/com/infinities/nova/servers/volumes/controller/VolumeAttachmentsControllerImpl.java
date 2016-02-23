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
package com.infinities.nova.servers.volumes.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.container.ContainerRequestContext;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.exception.VolumeNotFoundException;
import com.infinities.nova.servers.volumes.api.VolumeAttachmentsApi;
import com.infinities.nova.servers.volumes.model.VolumeAttachment;
import com.infinities.nova.servers.volumes.model.VolumeAttachmentTemplate;
import com.infinities.nova.servers.volumes.model.VolumeAttachments;
import com.infinities.nova.servers.volumes.view.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class VolumeAttachmentsControllerImpl implements VolumeAttachmentsController {

	// private final static Logger logger =
	// LoggerFactory.getLogger(VolumeAttachmentsControllerImpl.class);
	private final ViewBuilder builder = new ViewBuilder();
	private final VolumeAttachmentsApi volumeAttachmentsApi;


	public VolumeAttachmentsControllerImpl(VolumeAttachmentsApi volumeAttachmentsApi) {
		this.volumeAttachmentsApi = volumeAttachmentsApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.volumes.controller.VolumeAttachmentsController
	 * #index(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public VolumeAttachments index(ContainerRequestContext requestContext, String projectId, String serverId)
			throws InternalException, CloudException, ConcurrentException, Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<VolumeAttachment> volumeAttachments = volumeAttachmentsApi.getVolumeAttachments(context, projectId, serverId);
		return builder.index(requestContext, volumeAttachments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.volumes.controller.VolumeAttachmentsController
	 * #show(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public VolumeAttachmentTemplate show(ContainerRequestContext requestContext, String projectId, String serverId,
			String volumeId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		VolumeAttachment volumeAttachment = volumeAttachmentsApi.getVolumeAttachment(context, projectId, serverId, volumeId);
		if (volumeAttachment == null) {
			throw new VolumeNotFoundException();
		}
		return builder.show(requestContext, volumeAttachment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.volumes.controller.VolumeAttachmentsController
	 * #delete(java.lang.String, java.lang.String, java.lang.String,
	 * javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void detach(String projectId, String serverId, String volumeId, ContainerRequestContext requestContext)
			throws InternalException, CloudException, ConcurrentException, InterruptedException, ExecutionException,
			Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		volumeAttachmentsApi.detach(context, projectId, serverId, volumeId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.volumes.controller.VolumeAttachmentsController
	 * #attach(java.lang.String, java.lang.String,
	 * com.infinities.nova.servers.volumes.model.VolumeAttachmentTemplate,
	 * javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public VolumeAttachmentTemplate attach(String projectId, String serverId, VolumeAttachmentTemplate volumeAttachment,
			ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		VolumeAttachment ret =
				volumeAttachmentsApi.attach(context, projectId, serverId, volumeAttachment.getVolumeAttachment()
						.getVolumeId(), volumeAttachment.getVolumeAttachment().getDevice());
		if (Strings.isNullOrEmpty(ret.getDevice())) {
			ret.setDevice(volumeAttachment.getVolumeAttachment().getDevice());
		}
		if (Strings.isNullOrEmpty(ret.getServerId())) {
			ret.setServerId(serverId);
		}
		return builder.show(requestContext, ret);
	}

}
