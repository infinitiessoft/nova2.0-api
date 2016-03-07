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
package com.infinities.nova.servers.interfaces.api;

import java.util.List;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachment;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentForCreateTemplate;

/**
 * @author pohsun
 *
 */
public interface InterfaceAttachmentsApi {

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	List<InterfaceAttachment> getInterfaceAttachments(OpenstackRequestContext context, String projectId, String serverId)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @param interfaceAttachmentId
	 * @return
	 * @throws Exception
	 */
	InterfaceAttachment getInterfaceAttachment(OpenstackRequestContext context, String projectId, String serverId,
			String interfaceAttachmentId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @param interfaceAttachmentForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	InterfaceAttachment attach(OpenstackRequestContext context, String projectId, String serverId,
			InterfaceAttachmentForCreateTemplate interfaceAttachmentForCreateTemplate) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @param interfaceAttachmentId
	 * @throws Exception
	 */
	void detach(OpenstackRequestContext context, String projectId, String serverId, String interfaceAttachmentId)
			throws Exception;

}
