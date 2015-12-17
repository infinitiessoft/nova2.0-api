package com.infinities.nova.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.exception.http.HTTPMethodNotAllowException;
import com.infinities.nova.api.openstack.common.template.MetaItemTemplate;
import com.infinities.nova.api.openstack.common.template.MetadataTemplate;
import com.infinities.nova.api.openstack.compute.images.ImageMetadataController;
import com.infinities.nova.api.openstack.compute.images.ImageTemplate;
import com.infinities.nova.api.openstack.compute.images.ImagesController;
import com.infinities.nova.api.openstack.compute.images.ImagesTemplate;
import com.infinities.nova.api.openstack.compute.images.MinimalImagesTemplate;
import com.infinities.nova.api.openstack.wsgi.Resource;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagesResource {

	private final ImagesController controller;
	private final ImageMetadataController metadataController;


	@Inject
	public ImagesResource(ImagesController controller, ImageMetadataController metadataController) {
		this.controller = controller;
		this.metadataController = metadataController;
	}

	@GET
	public MinimalImagesTemplate index(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.index(requestContext);
	}

	@GET
	@Path("detail")
	public ImagesTemplate datail(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.detail(requestContext);
	}

	@GET
	@Path("{imageId}")
	public ImageTemplate show(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.show(imageId, requestContext);
	}

	@DELETE
	@Path("{imageId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return controller.delete(imageId, requestContext);
	}

	@POST
	public Response create() throws Exception {
		throw new HTTPMethodNotAllowException();
	}

	@GET
	@Path("{imageId}/metadata")
	public MetadataTemplate indexMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return metadataController.index(imageId, requestContext);
	}

	@GET
	@Path("{imageId}/metadata/{key}")
	public MetaItemTemplate showMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@PathParam("key") String key, @Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return metadataController.show(imageId, key, requestContext);
	}

	@POST
	@Path("{imageId}/metadata")
	public MetadataTemplate createMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			MetadataTemplate metadata, @Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return metadataController.create(imageId, metadata, requestContext);
	}

	@PUT
	@Path("{imageId}/metadata/{key}")
	public MetaItemTemplate updateMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@PathParam("key") String key, MetaItemTemplate meta, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return metadataController.update(imageId, key, meta, requestContext);
	}

	@PUT
	@Path("{imageId}/metadata")
	public MetadataTemplate updateAllMetadata(@PathParam("projectId") String projectId,
			@PathParam("imageId") String imageId, MetadataTemplate metadata, @Context ContainerRequestContext requestContext)
			throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return metadataController.updataAll(imageId, metadata, requestContext);
	}

	@DELETE
	@Path("{imageId}/metadata/{key}")
	public Response deleteMetadata(@PathParam("projectId") String projectId, @PathParam("imageId") String imageId,
			@PathParam("key") String key, @Context ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext novaContext = (NovaRequestContext) requestContext.getProperty("nova.context");
		Resource.processStack(requestContext, projectId, novaContext);
		return metadataController.delete(imageId, key, requestContext);
	}

}
