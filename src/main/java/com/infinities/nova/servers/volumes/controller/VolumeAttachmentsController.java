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

import java.util.concurrent.ExecutionException;

import javax.ws.rs.container.ContainerRequestContext;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import com.infinities.nova.servers.volumes.model.VolumeAttachmentTemplate;
import com.infinities.nova.servers.volumes.model.VolumeAttachments;

/**
 * @author pohsun
 *
 */
public interface VolumeAttachmentsController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 * @throws ConcurrentException
	 * @throws CloudException
	 * @throws InternalException
	 */
	VolumeAttachments index(ContainerRequestContext requestContext, String projectId, String serverId)
			throws InternalException, CloudException, ConcurrentException, Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	VolumeAttachmentTemplate show(ContainerRequestContext requestContext, String projectId, String serverId, String volumeId)
			throws Exception;

	/**
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param requestContext
	 * @throws Exception
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws ConcurrentException
	 * @throws CloudException
	 * @throws InternalException
	 */
	void detach(String projectId, String serverId, String volumeId, ContainerRequestContext requestContext)
			throws InternalException, CloudException, ConcurrentException, InterruptedException, ExecutionException,
			Exception;

	/**
	 * @param projectId
	 * @param serverId
	 * @param volumeAttachment
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	VolumeAttachmentTemplate attach(String projectId, String serverId, VolumeAttachmentTemplate volumeAttachment,
			ContainerRequestContext requestContext) throws Exception;

}
