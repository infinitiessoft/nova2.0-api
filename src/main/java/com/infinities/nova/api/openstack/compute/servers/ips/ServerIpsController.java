package com.infinities.nova.api.openstack.compute.servers.ips;

import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.response.model.Server.Addresses;
import com.infinities.nova.response.model.Server.Addresses.Address;

public interface ServerIpsController {

	Addresses index(ContainerRequestContext requestContext, String serverId) throws Exception;

	Map<String, List<Address>> show(ContainerRequestContext requestContext, String serverId, String networkLabel)
			throws Exception;

}
