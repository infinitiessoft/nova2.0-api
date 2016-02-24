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
package com.infinities.nova.securitygroups.view;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.securitygroups.model.SecurityGroup;
import com.infinities.nova.securitygroups.model.SecurityGroupTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroups;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param securityGroup
	 * @return
	 */
	public SecurityGroupTemplate show(ContainerRequestContext requestContext, SecurityGroup securityGroup) {
		SecurityGroupTemplate template = new SecurityGroupTemplate();
		template.setSecurityGroup(securityGroup);
		return template;
	}

	/**
	 * @param requestContext
	 * @param securityGroups
	 * @return
	 */
	public SecurityGroups index(ContainerRequestContext requestContext, List<SecurityGroup> securityGroups) {
		SecurityGroups ret = new SecurityGroups();
		ret.setList(securityGroups);
		return ret;
	}

}
