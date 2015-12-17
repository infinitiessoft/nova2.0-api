package com.infinities.nova.api.openstack.compute.images;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

public interface ImagesController {

	MinimalImagesTemplate index(ContainerRequestContext requestContext) throws Exception;

	ImagesTemplate detail(ContainerRequestContext requestContext) throws Exception;

	ImageTemplate show(String imageId, ContainerRequestContext requestContext) throws Exception;

	Response delete(String imageId, ContainerRequestContext requestContext) throws Exception;

}
