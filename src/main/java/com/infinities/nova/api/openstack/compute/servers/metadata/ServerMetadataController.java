package com.infinities.nova.api.openstack.compute.servers.metadata;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.api.openstack.common.template.MetaItemTemplate;
import com.infinities.nova.api.openstack.common.template.MetadataTemplate;

public interface ServerMetadataController {

	MetadataTemplate index(ContainerRequestContext requestContext, String serverId) throws Exception;

	MetadataTemplate create(ContainerRequestContext requestContext, String serverId, MetadataTemplate metadata)
			throws Exception;

	MetaItemTemplate update(ContainerRequestContext requestContext, String serverId, String key, MetaItemTemplate meta)
			throws Exception;

	MetadataTemplate updateAll(ContainerRequestContext requestContext, String serverId, MetadataTemplate metadata)
			throws Exception;

	MetaItemTemplate show(ContainerRequestContext requestContext, String serverId, String key) throws Exception;

	void delete(ContainerRequestContext requestContext, String serverId, String key) throws Exception;

}
