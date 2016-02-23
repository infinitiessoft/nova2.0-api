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
package com.infinities.nova.volumes.controller;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.volumes.api.VolumesApi;
import com.infinities.nova.volumes.model.Volume;
import com.infinities.nova.volumes.model.VolumeForCreateTemplate;
import com.infinities.nova.volumes.model.VolumeTemplate;
import com.infinities.nova.volumes.model.Volumes;
import com.infinities.nova.volumes.views.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class VolumesControllerImpl implements VolumesController {

	private final VolumesApi volumesApi;
	private final ViewBuilder builder = new ViewBuilder();


	public VolumesControllerImpl(VolumesApi volumesApi) {
		this.volumesApi = volumesApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.controller.VolumesController#index(javax.
	 * ws.rs.container.ContainerRequestContext, java.lang.String)
	 */
	@Override
	public Volumes index(ContainerRequestContext requestContext, String projectId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<Volume> volumes = volumesApi.getVolumes(context, projectId);
		return builder.index(requestContext, volumes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.controller.VolumesController#show(javax.ws
	 * .rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public VolumeTemplate show(ContainerRequestContext requestContext, String projectId, String volumeId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Volume volume = volumesApi.getVolume(context, projectId, volumeId);

		return builder.show(requestContext, volume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.controller.VolumesController#create(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String,
	 * com.infinities.nova.volumes.model.VolumeForCreateTemplate)
	 */
	@Override
	public VolumeTemplate create(ContainerRequestContext requestContext, String projectId,
			VolumeForCreateTemplate volumeForCreateTemplate) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Volume volume = volumesApi.createVolume(context, projectId, volumeForCreateTemplate);

		return builder.show(requestContext, volume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.volumes.controller.VolumesController#delete(java.
	 * lang.String, java.lang.String,
	 * javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void delete(String projectId, String volumeId, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		volumesApi.deleteVolume(context, projectId, volumeId);
	}

}
