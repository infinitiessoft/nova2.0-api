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
package com.infinities.nova.securitygroups.rules.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule;
import com.infinities.nova.securitygroups.rules.api.SecurityGroupRulesApi;
import com.infinities.nova.securitygroups.rules.model.SecurityGroupRuleForCreateTemplate;
import com.infinities.nova.securitygroups.rules.model.SecurityGroupRuleTemplate;
import com.infinities.nova.securitygroups.rules.view.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class SecurityGroupRulesControllerImpl implements SecurityGroupRulesController {

	private final SecurityGroupRulesApi securityGroupRulesApi;
	private final ViewBuilder builder = new ViewBuilder();


	public SecurityGroupRulesControllerImpl(SecurityGroupRulesApi securityGroupRulesApi) {
		this.securityGroupRulesApi = securityGroupRulesApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.securitygroups.rules.controller.
	 * SecurityGroupRulesController
	 * #create(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * com.infinities.nova.securitygroups.rules.model.
	 * SecurityGroupRuleForCreateTemplate)
	 */
	@Override
	public SecurityGroupRuleTemplate create(ContainerRequestContext requestContext, String projectId,
			SecurityGroupRuleForCreateTemplate body) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Rule rule = securityGroupRulesApi.createRule(context, projectId, body);

		return builder.show(requestContext, rule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.securitygroups.rules.controller.
	 * SecurityGroupRulesController
	 * #delete(javax.ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void delete(ContainerRequestContext requestContext, String projectId, String securityGroupRuleId)
			throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		securityGroupRulesApi.deleteRule(context, projectId, securityGroupRuleId);
	}

}
