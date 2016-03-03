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
package com.infinities.nova.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infinities.nova.namebinding.CheckProjectId;
import com.infinities.nova.securitygroups.rules.controller.SecurityGroupRulesController;
import com.infinities.nova.securitygroups.rules.model.SecurityGroupRuleForCreateTemplate;
import com.infinities.nova.securitygroups.rules.model.SecurityGroupRuleTemplate;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
public class SecurityGroupRulesResource {

	private final SecurityGroupRulesController controller;


	@Autowired
	public SecurityGroupRulesResource(SecurityGroupRulesController controller) {
		this.controller = controller;
	}

	@POST
	public SecurityGroupRuleTemplate create(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext, SecurityGroupRuleForCreateTemplate body) throws Exception {

		return controller.create(requestContext, projectId, body);
	}

	@DELETE
	@Path("{securityGroupRuleId}")
	public Response delete(@PathParam("projectId") String projectId,
			@PathParam("securityGroupRuleId") String securityGroupRuleId, @Context ContainerRequestContext requestContext)
			throws Exception {

		controller.delete(requestContext, projectId, securityGroupRuleId);
		return Response.status(Status.ACCEPTED).build();
	}
}
