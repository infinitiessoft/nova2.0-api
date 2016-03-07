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
package com.infinities.nova.volumes.api;

import java.util.List;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.volumes.model.Volume;
import com.infinities.nova.volumes.model.VolumeForCreateTemplate;

/**
 * @author pohsun
 *
 */
public interface VolumesApi {

	/**
	 * @param context
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	List<Volume> getVolumes(OpenstackRequestContext context, String projectId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	Volume getVolume(OpenstackRequestContext context, String projectId, String volumeId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param volumeForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Volume createVolume(OpenstackRequestContext context, String projectId, VolumeForCreateTemplate volumeForCreateTemplate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param volumeId
	 * @throws Exception
	 */
	void deleteVolume(OpenstackRequestContext context, String projectId, String volumeId) throws Exception;

}
