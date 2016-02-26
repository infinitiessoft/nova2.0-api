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

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.response.model.ServerForCreate;
import com.infinities.nova.servers.controller.ServersFilter;

public interface ComputeApi {

	// expectedAttrs=null
	List<Instance> getAll(NovaRequestContext context, ServersFilter searchOpts, String sortKey, String sortDir,
			Integer limit, String marker, List<String> expectedAttrs) throws Exception;

	// expectedAttrs=null
	Instance get(NovaRequestContext context, String serverId, List<String> expectedAttrs) throws Exception;

	void delete(NovaRequestContext context, Instance instance) throws Exception;

	Instance update(NovaRequestContext context, String serverId, String name, String ipv4, String ipv6) throws Exception;

	Map<String, String> getInstanceMetadata(NovaRequestContext context, Instance server) throws Exception;

	Map<String, String> updateInstanceMetadata(NovaRequestContext context, Instance server, Map<String, String> metadata,
			boolean delete) throws Exception;

	void deleteInstanceMetadata(NovaRequestContext context, Instance server, String key) throws Exception;

	void resize(NovaRequestContext context, Instance instance, String flavorId, String autoDiskConfig) throws Exception;

	void reboot(NovaRequestContext context, Instance instance, String rebootType) throws Exception;

	void revertResize(NovaRequestContext context, Instance instance) throws Exception;

	void confirmResize(NovaRequestContext context, Instance instance) throws Exception;

	void setAdminPassword(NovaRequestContext context, Instance instance, String adminPass) throws Exception;

	void rebuild(NovaRequestContext context, Instance instance, String imageHref, String password, String accessIpV4,
			String accessIpV6, String name, Map<String, String> metadata, String diskConfig) throws Exception;

	void snapshot(NovaRequestContext context, Instance instance, String imageName, Map<String, String> metadata)
			throws Exception;

	void pause(NovaRequestContext context, Instance instance) throws Exception;

	void unpause(NovaRequestContext context, Instance instance) throws Exception;

	void suspend(NovaRequestContext context, Instance instance) throws Exception;

	void resume(NovaRequestContext context, Instance instance) throws Exception;

	void start(NovaRequestContext context, Instance instance) throws Exception;

	void stop(NovaRequestContext context, Instance instance) throws Exception;

	Entry<List<Instance>, UUID> create(NovaRequestContext context, String flavorId, String imageHref, String kernelId,
			String ramDiskId, Integer minCount, Integer maxCount, String displayName, String displayDescription,
			String keyName, String keyData, List<String> securityGroup, String availabilityZone, String userData,
			Map<String, String> metadata, List<Entry<String, String>> injectedFiles, String adminPassword,
			String accessIpV4, String accessIpV6, List<ServerForCreate.NetworkForCreate> requestedNetworks,
			boolean configDrive, boolean autoDiskConfig, boolean checkServerGroupQuota) throws Exception;

}
