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
package com.infinities.nova.volumes.views;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.volumes.model.Volume;
import com.infinities.nova.volumes.model.VolumeTemplate;
import com.infinities.nova.volumes.model.Volumes;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param volumes
	 * @return
	 */
	public Volumes index(ContainerRequestContext requestContext, List<Volume> volumes) {
		Volumes ret = new Volumes();
		ret.setList(volumes);
		return ret;
	}

	/**
	 * @param requestContext
	 * @param volume
	 * @return
	 */
	public VolumeTemplate show(ContainerRequestContext requestContext, Volume volume) {
		VolumeTemplate template = new VolumeTemplate();
		template.setVolume(volume);
		return template;
	}

}
