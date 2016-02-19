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
package com.infinities.nova.servers.metadata.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.common.model.MetaItemTemplate;
import com.infinities.nova.common.model.MetadataTemplate;

public interface ServerMetadataController {

	MetadataTemplate index(ContainerRequestContext requestContext, String serverId) throws Exception;

	MetadataTemplate create(ContainerRequestContext requestContext, String serverId, MetadataTemplate metadata)
			throws Exception;

	MetaItemTemplate update(ContainerRequestContext requestContext, String serverId, String key, MetaItemTemplate meta)
			throws Exception;

	MetadataTemplate updateAll(ContainerRequestContext requestContext, String serverId, MetadataTemplate metadata)
			throws Exception;

	MetaItemTemplate show(ContainerRequestContext requestContext, String serverId, String key) throws Exception;

	void delete(ContainerRequestContext requestContext, String serverId, String key) throws Exception;

}
