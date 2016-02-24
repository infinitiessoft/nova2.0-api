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
package com.infinities.nova.securitygroups.controller;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.securitygroups.model.SecurityForCreateTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroupTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroups;

/**
 * @author pohsun
 *
 */
public interface SecurityGroupsController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @param body
	 * @return
	 * @throws Exception
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	SecurityGroupTemplate create(ContainerRequestContext requestContext, String projectId, SecurityForCreateTemplate body)
			throws InterruptedException, ExecutionException, Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	SecurityGroups index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param securityGroupId
	 * @return
	 * @throws Exception
	 */
	SecurityGroupTemplate show(ContainerRequestContext requestContext, String projectId, String securityGroupId)
			throws Exception;

	/**
	 * @param projectId
	 * @param requestContext
	 * @param securityGroupId
	 * @param body
	 * @return
	 * @throws Exception
	 */
	SecurityGroupTemplate update(String projectId, ContainerRequestContext requestContext, String securityGroupId,
			SecurityGroupTemplate body) throws Exception;

	/**
	 * @param projectId
	 * @param securityGroupId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String securityGroupId, ContainerRequestContext requestContext) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	SecurityGroups index(ContainerRequestContext requestContext, String projectId, String serverId) throws Exception;
}
