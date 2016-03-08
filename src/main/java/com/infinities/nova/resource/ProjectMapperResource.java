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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.infinities.api.openstack.commons.dynamicfeature.OpenstackContext;
import com.infinities.api.openstack.commons.namebinding.CheckProjectId;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
@OpenstackContext
public class ProjectMapperResource {

	@Path("limits")
	public Class<LimitsResource> getLimitsResource() {
		return LimitsResource.class;
	}

	@Path("flavors")
	public Class<FlavorsResource> getFlavorsResource() {
		return FlavorsResource.class;
	}

	@Path("images")
	public Class<ImagesResource> getImagesResource() {
		return ImagesResource.class;
	}

	@Path("servers")
	public Class<ServersResource> getServersResource() {
		return ServersResource.class;
	}

	@Path("os-keypairs")
	public Class<KeyPairsResource> getKeyPairsResource() {
		return KeyPairsResource.class;
	}

	@Path("os-availability-zone")
	public Class<AvailabilityZoneResource> getAvailabilityZoneResource() {
		return AvailabilityZoneResource.class;
	}

	@Path("os-volumes")
	public Class<VolumesResource> getVolumesResource() {
		return VolumesResource.class;
	}

	@Path("os-snapshots")
	public Class<SnapshotsResource> getSnapshotsResource() {
		return SnapshotsResource.class;
	}

	@Path("os-security-groups")
	public Class<SecurityGroupsResource> getSecurityGroupsResource() {
		return SecurityGroupsResource.class;
	}

	@Path("os-security-group-rules")
	public Class<SecurityGroupRulesResource> getSecurityGroupRulesResource() {
		return SecurityGroupRulesResource.class;
	}

	@Path("os-networks")
	public Class<NetworksResource> getNetworksResource() {
		return NetworksResource.class;
	}

}
