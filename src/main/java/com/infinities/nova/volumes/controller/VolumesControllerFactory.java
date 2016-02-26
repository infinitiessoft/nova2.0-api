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

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.volumes.api.VolumesApi;

public class VolumesControllerFactory implements Factory<VolumesController> {

	private final VolumesApi volumesApi;


	@Inject
	public VolumesControllerFactory(VolumesApi volumesApi) {
		this.volumesApi = volumesApi;
	}

	@Override
	public VolumesController provide() {
		return new VolumesControllerImpl(volumesApi);
	}

	@Override
	public void dispose(VolumesController instance) {
	}

}