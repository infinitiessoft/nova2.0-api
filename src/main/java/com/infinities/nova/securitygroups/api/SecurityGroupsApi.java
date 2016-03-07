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
package com.infinities.nova.securitygroups.api;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.securitygroups.model.SecurityForCreateTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroup;
import com.infinities.nova.securitygroups.model.SecurityGroupTemplate;

/**
 * @author pohsun
 *
 */
public interface SecurityGroupsApi {

	/**
	 * @param context
	 * @param projectId
	 * @param body
	 * @return
	 * @throws Exception
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	SecurityGroup createSecurityGroup(OpenstackRequestContext context, String projectId, SecurityForCreateTemplate body)
			throws InterruptedException, ExecutionException, Exception;

	/**
	 * @param context
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	List<SecurityGroup> getSecurityGroups(OpenstackRequestContext context, String projectId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param securityGroupId
	 * @return
	 * @throws Exception
	 */
	SecurityGroup getSecurityGroup(OpenstackRequestContext context, String projectId, String securityGroupId)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param securityGroupId
	 * @throws Exception
	 */
	void deleteSecurityGroup(OpenstackRequestContext context, String projectId, String securityGroupId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param securityGroupId
	 * @param body
	 * @return
	 * @throws Exception
	 */
	SecurityGroup updateSecurityGroup(OpenstackRequestContext context, String projectId, String securityGroupId,
			SecurityGroupTemplate body) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	List<SecurityGroup> getSecurityGroups(OpenstackRequestContext context, String projectId, String serverId)
			throws Exception;

}
