package com.infinities.nova.api.openstack.compute.images;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.infinities.nova.api.openstack.common.template.MetaItemTemplate;
import com.infinities.nova.api.openstack.common.template.MetadataTemplate;

public interface ImageMetadataController {

	MetadataTemplate index(String imageId, ContainerRequestContext requestContext) throws Exception;

	MetaItemTemplate show(String imageId, String key, ContainerRequestContext requestContext) throws Exception;

	MetadataTemplate create(String imageId, MetadataTemplate metadata, ContainerRequestContext requestContext)
			throws Exception;

	MetaItemTemplate update(String imageId, String key, MetaItemTemplate meta, ContainerRequestContext requestContext)
			throws Exception;

	Response delete(String imageId, String key, ContainerRequestContext requestContext) throws Exception;

	MetadataTemplate updataAll(String imageId, MetadataTemplate metadata, ContainerRequestContext requestContext)
			throws Exception;

}
