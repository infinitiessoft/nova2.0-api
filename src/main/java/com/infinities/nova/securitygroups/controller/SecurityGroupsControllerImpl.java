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

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.securitygroups.api.SecurityGroupsApi;
import com.infinities.nova.securitygroups.model.SecurityForCreateTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroup;
import com.infinities.nova.securitygroups.model.SecurityGroupTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroups;
import com.infinities.nova.securitygroups.view.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class SecurityGroupsControllerImpl implements SecurityGroupsController {

	private final SecurityGroupsApi securityGroupsApi;
	private final ViewBuilder builder = new ViewBuilder();


	public SecurityGroupsControllerImpl(SecurityGroupsApi securityGroupsApi) {
		this.securityGroupsApi = securityGroupsApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.controller.SecurityGroupsController
	 * #create(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * com.infinities.nova.securitygroups.model.SecurityGroupTemplate)
	 */
	@Override
	public SecurityGroupTemplate create(ContainerRequestContext requestContext, String projectId,
			SecurityForCreateTemplate body) throws InterruptedException, ExecutionException, Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		SecurityGroup securityGroup = securityGroupsApi.createSecurityGroup(context, projectId, body);

		return builder.show(requestContext, securityGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.controller.SecurityGroupsController
	 * #index(javax.ws.rs.container.ContainerRequestContext, java.lang.String)
	 */
	@Override
	public SecurityGroups index(ContainerRequestContext requestContext, String projectId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		List<SecurityGroup> securityGroups = securityGroupsApi.getSecurityGroups(context, projectId);
		return builder.index(requestContext, securityGroups);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.controller.SecurityGroupsController
	 * #show(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SecurityGroupTemplate show(ContainerRequestContext requestContext, String projectId, String securityGroupId)
			throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		SecurityGroup securityGroup = securityGroupsApi.getSecurityGroup(context, projectId, securityGroupId);

		return builder.show(requestContext, securityGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.controller.SecurityGroupsController
	 * #update(java.lang.String, javax.ws.rs.container.ContainerRequestContext,
	 * com.infinities.nova.securitygroups.model.SecurityGroupTemplate)
	 */
	@Override
	public SecurityGroupTemplate update(String projectId, ContainerRequestContext requestContext, String securityGroupId,
			SecurityGroupTemplate body) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		SecurityGroup securityGroup = securityGroupsApi.updateSecurityGroup(context, projectId, securityGroupId, body);

		return builder.show(requestContext, securityGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.controller.SecurityGroupsController
	 * #delete(java.lang.String, java.lang.String,
	 * javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void delete(String projectId, String securityGroupId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		securityGroupsApi.deleteSecurityGroup(context, projectId, securityGroupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.controller.SecurityGroupsController
	 * #index(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SecurityGroups index(ContainerRequestContext requestContext, String projectId, String serverId) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		List<SecurityGroup> securityGroups = securityGroupsApi.getSecurityGroups(context, projectId, serverId);
		return builder.index(requestContext, securityGroups);
	}

}
