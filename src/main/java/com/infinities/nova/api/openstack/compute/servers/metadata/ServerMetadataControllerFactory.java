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
package com.infinities.nova.api.openstack.compute.servers.metadata;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.openstack.compute.servers.api.ComputeApi;

public class ServerMetadataControllerFactory implements Factory<ServerMetadataController> {

	private final ComputeApi computesApi;


	@Inject
	public ServerMetadataControllerFactory(ComputeApi computesApi) {
		this.computesApi = computesApi;
	}

	@Override
	public ServerMetadataController provide() {
		return new ServerMetadataControllerImpl(computesApi);
	}

	@Override
	public void dispose(ServerMetadataController instance) {
	}

}
