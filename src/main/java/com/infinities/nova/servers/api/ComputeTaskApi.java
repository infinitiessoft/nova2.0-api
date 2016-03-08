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

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.images.model.Image;
import com.infinities.nova.servers.model.Server;
import com.infinities.nova.servers.model.ServerForCreate;

public interface ComputeTaskApi {

	public List<Server> buildInstances(OpenstackRequestContext context, CreateVmBaseOptions options, int num,
			Image bootMeta, String adminPassword, List<Entry<String, String>> injectedFiles,
			List<ServerForCreate.NetworkForCreate> requestedNetworks, List<String> securityGroups) throws Exception;

	public void terminateInstance(OpenstackRequestContext context, Server instance, List<String> reservations)
			throws Exception;

	public Server updateInstance(OpenstackRequestContext context, String serverId, String name, String ipv4, String ipv6)
			throws Exception;

	public void deleteInstanceMetadata(OpenstackRequestContext context, Server instance, String key) throws Exception;

	public void updateInstanceMetadata(OpenstackRequestContext context, Server instance, Map<String, String> metadata,
			boolean delete) throws Exception;

	public void resizeInstance(OpenstackRequestContext context, Server instance, String newInstanceTypeId,
			boolean cleanShutdown) throws Exception;
}
