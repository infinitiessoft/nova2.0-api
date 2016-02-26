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
package com.infinities.nova.servers.interfaces.views;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.servers.interfaces.model.InterfaceAttachment;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentTemplate;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachments;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param interfaceAttachmentAttachments
	 * @return
	 */
	public InterfaceAttachments index(ContainerRequestContext requestContext,
			List<InterfaceAttachment> interfaceAttachmentAttachments) {
		InterfaceAttachments interfaceAttachments = new InterfaceAttachments();
		interfaceAttachments.setList(interfaceAttachmentAttachments);
		return interfaceAttachments;
	}

	/**
	 * @param requestContext
	 * @param interfaceAttachmentAttachment
	 * @return
	 */
	public InterfaceAttachmentTemplate show(ContainerRequestContext requestContext, InterfaceAttachment interfaceAttachment) {
		InterfaceAttachmentTemplate template = new InterfaceAttachmentTemplate();
		template.setInterfaceAttachment(interfaceAttachment);
		return template;
	}

}
