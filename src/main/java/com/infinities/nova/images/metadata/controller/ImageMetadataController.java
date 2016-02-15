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
package com.infinities.nova.images.metadata.controller;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.infinities.nova.api.openstack.compute.model.MetaItemTemplate;
import com.infinities.nova.api.openstack.compute.model.MetadataTemplate;

public interface ImageMetadataController {

	MetadataTemplate index(String imageId, ContainerRequestContext requestContext) throws Exception;

	MetaItemTemplate show(String imageId, String key, ContainerRequestContext requestContext) throws Exception;

	MetadataTemplate create(String imageId, MetadataTemplate metadata, ContainerRequestContext requestContext)
			throws Exception;

	MetaItemTemplate update(String imageId, String key, MetaItemTemplate meta, ContainerRequestContext requestContext)
			throws Exception;

	Response delete(String imageId, String key, ContainerRequestContext requestContext) throws Exception;

	MetadataTemplate updataAll(String imageId, MetadataTemplate metadata, ContainerRequestContext requestContext)
			throws Exception;

}
