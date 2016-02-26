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
package com.infinities.nova.networks.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.networks.model.NetworkForCreateTemplate;
import com.infinities.nova.networks.model.NetworkTemplate;
import com.infinities.nova.networks.model.Networks;

/**
 * @author pohsun
 *
 */
public interface NetworksController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	Networks index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param networkId
	 * @return
	 * @throws Exception
	 */
	NetworkTemplate show(ContainerRequestContext requestContext, String projectId, String networkId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param networkForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	NetworkTemplate create(ContainerRequestContext requestContext, String projectId,
			NetworkForCreateTemplate networkForCreateTemplate) throws Exception;

	/**
	 * @param projectId
	 * @param networkId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String networkId, ContainerRequestContext requestContext) throws Exception;

}
