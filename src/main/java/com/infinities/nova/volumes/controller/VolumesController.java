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

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.volumes.model.VolumeForCreateTemplate;
import com.infinities.nova.volumes.model.VolumeTemplate;
import com.infinities.nova.volumes.model.Volumes;

/**
 * @author pohsun
 *
 */
public interface VolumesController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	Volumes index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	VolumeTemplate show(ContainerRequestContext requestContext, String projectId, String volumeId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param volumeForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	VolumeTemplate create(ContainerRequestContext requestContext, String projectId,
			VolumeForCreateTemplate volumeForCreateTemplate) throws Exception;

	/**
	 * @param projectId
	 * @param volumeId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String volumeId, ContainerRequestContext requestContext) throws Exception;

}
