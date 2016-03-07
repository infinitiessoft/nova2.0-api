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

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.exception.InterfaceAttachmentNotFoundException;
import com.infinities.nova.servers.interfaces.api.InterfaceAttachmentsApi;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachment;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentForCreateTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachments;
import com.infinities.nova.servers.interfaces.views.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class InterfaceAttachmentsControllerImpl implements InterfaceAttachmentsController {

	private final ViewBuilder builder = new ViewBuilder();
	private final InterfaceAttachmentsApi interfaceAttachmentsApi;


	public InterfaceAttachmentsControllerImpl(InterfaceAttachmentsApi interfaceAttachmentsApi) {
		this.interfaceAttachmentsApi = interfaceAttachmentsApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.interfaces.controller.
	 * InterfaceAttachmentsController
	 * #index(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public InterfaceAttachments index(ContainerRequestContext requestContext, String projectId, String serverId)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		List<InterfaceAttachment> interfaceAttachmentAttachments =
				interfaceAttachmentsApi.getInterfaceAttachments(context, projectId, serverId);
		return builder.index(requestContext, interfaceAttachmentAttachments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.interfaces.controller.
	 * InterfaceAttachmentsController
	 * #show(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public InterfaceAttachmentTemplate show(ContainerRequestContext requestContext, String projectId, String serverId,
			String interfaceAttachmentId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		InterfaceAttachment interfaceAttachment =
				interfaceAttachmentsApi.getInterfaceAttachment(context, projectId, serverId, interfaceAttachmentId);
		if (interfaceAttachment == null) {
			throw new InterfaceAttachmentNotFoundException();
		}
		return builder.show(requestContext, interfaceAttachment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.interfaces.controller.
	 * InterfaceAttachmentsController
	 * #create(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String, com.infinities.nova.servers.interfaces.model.
	 * InterfaceAttachmentForCreateTemplate)
	 */
	@Override
	public InterfaceAttachmentTemplate attach(ContainerRequestContext requestContext, String projectId, String serverId,
			InterfaceAttachmentForCreateTemplate interfaceAttachmentForCreateTemplate) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		InterfaceAttachment ret =
				interfaceAttachmentsApi.attach(context, projectId, serverId, interfaceAttachmentForCreateTemplate);

		return builder.show(requestContext, ret);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.interfaces.controller.
	 * InterfaceAttachmentsController#delete(java.lang.String, java.lang.String,
	 * java.lang.String, javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void detach(String projectId, String serverId, String interfaceAttachmentId,
			ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		interfaceAttachmentsApi.detach(context, projectId, serverId, interfaceAttachmentId);
	}

}
