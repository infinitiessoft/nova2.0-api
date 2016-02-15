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
package com.infinities.nova.servers.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.response.model.NetworkForCreate;

public interface ComputeTaskApi {

	public List<Instance> buildInstances(NovaRequestContext context, CreateVmBaseOptions options, int num, Image bootMeta,
			String adminPassword, List<Entry<String, String>> injectedFiles, List<NetworkForCreate> requestedNetworks,
			List<String> securityGroups) throws Exception;

	public void terminateInstance(NovaRequestContext context, Instance instance, List<String> reservations) throws Exception;

	public Instance updateInstance(NovaRequestContext context, String serverId, String name, String ipv4, String ipv6)
			throws Exception;

	public void deleteInstanceMetadata(NovaRequestContext context, Instance instance, String key) throws Exception;

	public void updateInstanceMetadata(NovaRequestContext context, Instance instance, Map<String, String> metadata,
			boolean delete) throws Exception;

	public void
			resizeInstance(NovaRequestContext context, Instance instance, String newInstanceTypeId, boolean cleanShutdown)
					throws Exception;
}
