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
package com.infinities.nova.servers.interfaces.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentForCreateTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachments;

/**
 * @author pohsun
 *
 */
public interface InterfaceAttachmentsController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	InterfaceAttachments index(ContainerRequestContext requestContext, String projectId, String serverId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param serverId
	 * @param interfaceAttachmentId
	 * @return
	 * @throws Exception
	 */
	InterfaceAttachmentTemplate show(ContainerRequestContext requestContext, String projectId, String serverId,
			String interfaceAttachmentId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param serverId
	 * @param interfaceAttachmentForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	InterfaceAttachmentTemplate attach(ContainerRequestContext requestContext, String projectId, String serverId,
			InterfaceAttachmentForCreateTemplate interfaceAttachmentForCreateTemplate) throws Exception;

	/**
	 * @param projectId
	 * @param serverId
	 * @param interfaceAttachmentId
	 * @param requestContext
	 * @throws Exception
	 */
	void detach(String projectId, String serverId, String interfaceAttachmentId, ContainerRequestContext requestContext)
			throws Exception;

}
