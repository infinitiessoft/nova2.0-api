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
package com.infinities.nova.servers.volumes.view;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.servers.volumes.model.VolumeAttachment;
import com.infinities.nova.servers.volumes.model.VolumeAttachmentTemplate;
import com.infinities.nova.servers.volumes.model.VolumeAttachments;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param volumeAttachments
	 * @return
	 */
	public VolumeAttachments index(ContainerRequestContext requestContext, List<VolumeAttachment> volumeAttachments) {
		VolumeAttachments rets = new VolumeAttachments();
		rets.setList(volumeAttachments);
		return rets;
	}

	/**
	 * @param requestContext
	 * @param volumeAttachment
	 * @return
	 */
	public VolumeAttachmentTemplate show(ContainerRequestContext requestContext, VolumeAttachment volumeAttachment) {
		VolumeAttachmentTemplate template = new VolumeAttachmentTemplate();
		template.setVolumeAttachment(volumeAttachment);
		return template;
	}

}
