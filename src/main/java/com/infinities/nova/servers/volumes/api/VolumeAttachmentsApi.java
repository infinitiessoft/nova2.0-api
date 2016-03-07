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

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.servers.volumes.model.VolumeAttachment;

/**
 * @author pohsun
 *
 */
public interface VolumeAttachmentsApi {

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws ConcurrentException
	 * @throws CloudException
	 * @throws InternalException
	 * @throws Exception
	 */
	List<VolumeAttachment> getVolumeAttachments(OpenstackRequestContext context, String projectId, String serverId)
			throws InternalException, CloudException, ConcurrentException, Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	VolumeAttachment getVolumeAttachment(OpenstackRequestContext context, String projectId, String serverId, String volumeId)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @throws ConcurrentException
	 * @throws CloudException
	 * @throws InternalException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws Exception
	 */
	void detach(OpenstackRequestContext context, String projectId, String serverId, String volumeId)
			throws InternalException, CloudException, ConcurrentException, InterruptedException, ExecutionException,
			Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param device
	 * @return
	 * @throws Exception
	 */
	VolumeAttachment attach(OpenstackRequestContext context, String projectId, String serverId, String volumeId,
			String device) throws Exception;

}
