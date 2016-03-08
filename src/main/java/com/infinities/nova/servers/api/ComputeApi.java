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
import java.util.UUID;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.servers.controller.ServersFilter;
import com.infinities.nova.servers.model.Server;
import com.infinities.nova.servers.model.ServerForCreate;

public interface ComputeApi {

	// expectedAttrs=null
	List<Server> getAll(OpenstackRequestContext context, ServersFilter searchOpts, String sortKey, String sortDir,
			Integer limit, String marker, List<String> expectedAttrs) throws Exception;

	// expectedAttrs=null
	Server get(OpenstackRequestContext context, String serverId, List<String> expectedAttrs) throws Exception;

	void delete(OpenstackRequestContext context, Server instance) throws Exception;

	Server update(OpenstackRequestContext context, String serverId, String name, String ipv4, String ipv6) throws Exception;

	Map<String, String> getInstanceMetadata(OpenstackRequestContext context, Server server) throws Exception;

	Map<String, String> updateInstanceMetadata(OpenstackRequestContext context, Server server, Map<String, String> metadata,
			boolean delete) throws Exception;

	void deleteInstanceMetadata(OpenstackRequestContext context, Server server, String key) throws Exception;

	void resize(OpenstackRequestContext context, Server instance, String flavorId, String autoDiskConfig) throws Exception;

	void reboot(OpenstackRequestContext context, Server instance, String rebootType) throws Exception;

	void revertResize(OpenstackRequestContext context, Server instance) throws Exception;

	void confirmResize(OpenstackRequestContext context, Server instance) throws Exception;

	void setAdminPassword(OpenstackRequestContext context, Server instance, String adminPass) throws Exception;

	void rebuild(OpenstackRequestContext context, Server instance, String imageHref, String password, String accessIpV4,
			String accessIpV6, String name, Map<String, String> metadata, String diskConfig) throws Exception;

	void snapshot(OpenstackRequestContext context, Server instance, String imageName, Map<String, String> metadata)
			throws Exception;

	void pause(OpenstackRequestContext context, Server instance) throws Exception;

	void unpause(OpenstackRequestContext context, Server instance) throws Exception;

	void suspend(OpenstackRequestContext context, Server instance) throws Exception;

	void resume(OpenstackRequestContext context, Server instance) throws Exception;

	void start(OpenstackRequestContext context, Server instance) throws Exception;

	void stop(OpenstackRequestContext context, Server instance) throws Exception;

	Entry<List<Server>, UUID> create(OpenstackRequestContext context, String flavorId, String imageHref, String kernelId,
			String ramDiskId, Integer minCount, Integer maxCount, String displayName, String displayDescription,
			String keyName, String keyData, List<String> securityGroup, String availabilityZone, String userData,
			Map<String, String> metadata, List<Entry<String, String>> injectedFiles, String adminPassword,
			String accessIpV4, String accessIpV6, List<ServerForCreate.NetworkForCreate> requestedNetworks,
			boolean configDrive, boolean autoDiskConfig, boolean checkServerGroupQuota) throws Exception;

}
