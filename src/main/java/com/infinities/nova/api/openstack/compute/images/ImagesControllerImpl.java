package com.infinities.nova.api.openstack.compute.images;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.keystonemiddleware.ImageNotAuthorizedException;
import com.infinities.keystonemiddleware.ImageNotFoundException;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.exception.InvalidException;
import com.infinities.nova.api.exception.InvalidImageRefException;
import com.infinities.nova.api.exception.NotFoundException;
import com.infinities.nova.api.exception.http.HTTPBadRequestException;
import com.infinities.nova.api.exception.http.HTTPForbiddenException;
import com.infinities.nova.api.exception.http.HTTPNotFoundException;
import com.infinities.nova.api.openstack.Common;
import com.infinities.nova.api.openstack.Common.PaginationParams;
import com.infinities.nova.api.openstack.compute.images.api.ImagesApi;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.views.images.ViewBuilder;
import com.infinities.skyport.util.FormatUtil;

public class ImagesControllerImpl implements ImagesController {

	private final static Logger logger = LoggerFactory.getLogger(ImagesControllerImpl.class);
	private final ViewBuilder builder = new ViewBuilder();
	private final ImagesApi imageApi;


	public ImagesControllerImpl(ImagesApi imageApi) {
		this.imageApi = imageApi;
	}

	@Override
	public MinimalImagesTemplate index(ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		ImagesFilter filters = getFilters(requestContext);
		PaginationParams pageParams = Common.getPaginationParams(requestContext);

		try {
			List<Image> images = imageApi.getAll(context, filters, pageParams);

			return builder.index(requestContext, images);
		} catch (InvalidException e) {
			logger.error("List images failed", e);
			throw new HTTPBadRequestException(e.getMessage());
		}
	}

	@Override
	public ImagesTemplate detail(ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		ImagesFilter filters = getFilters(requestContext);
		PaginationParams pageParams = Common.getPaginationParams(requestContext);

		try {
			List<Image> images = imageApi.getAll(context, filters, pageParams);
			// requestContext.cache_db_items("images", images, "id");
			return builder.detail(requestContext, images);
		} catch (InvalidException e) {
			logger.error("List images failed", e);
			throw new HTTPBadRequestException(e.getMessage());
		}
	}

	@Override
	public ImageTemplate show(String imageId, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");

		try {
			Image image = imageApi.get(context, imageId);
			logger.debug("Successfully retrieved image {}", imageId);
			List<Image> images = new ArrayList<Image>();
			images.add(image);
			// requestContext.cache_db_items("image", images, "id");
			return builder.show(requestContext, image);
		} catch (NotFoundException | InvalidImageRefException e) {
			logger.debug("image not found", e);
			String msg = "Image not found";
			throw new HTTPNotFoundException(msg);
			// throw new
			// WebApplicationException(Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

	}

	@Override
	public Response delete(String imageId, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		try {
			imageApi.delete(context, imageId);
		} catch (ImageNotFoundException e) {
			String msg = "Image not found";
			throw new HTTPNotFoundException(msg);
		} catch (ImageNotAuthorizedException e) {
			String msg = "You are not allowed to delete the image.";
			throw new HTTPForbiddenException(msg);
		}
		return Response.status(Status.NO_CONTENT).build();
	}

	private ImagesFilter getFilters(ContainerRequestContext requestContext) {
		ImagesFilter filter = new ImagesFilter();
		UriInfo uriInfo = requestContext.getUriInfo();

		String sortKey = uriInfo.getQueryParameters().getFirst("sort_key");
		if (Strings.isNullOrEmpty(sortKey)) {
			sortKey = "created_at";
		}
		filter.setSortKey(sortKey);
		String sortDir = uriInfo.getQueryParameters().getFirst("sort_dir");
		if (Strings.isNullOrEmpty(sortDir)) {
			sortDir = "desc";
		}
		filter.setSortDir(sortDir);

		String minRam = uriInfo.getQueryParameters().getFirst("minRam");
		Integer minMemoryMb = null;
		if (!Strings.isNullOrEmpty(minRam)) {
			minMemoryMb = Integer.parseInt(minRam);
			filter.setMinRam(minMemoryMb);
		}

		String minDisk = uriInfo.getQueryParameters().getFirst("minDisk");
		Integer minRootGb = null;
		if (!Strings.isNullOrEmpty(minDisk)) {
			minRootGb = Integer.parseInt(minDisk);
			filter.setMinDisk(minRootGb);
		}

		String name = uriInfo.getQueryParameters().getFirst("name");
		filter.setName(name);

		String status = uriInfo.getQueryParameters().getFirst("status");
		if (!Strings.isNullOrEmpty(status)) {
			filter.setStatus(status.toLowerCase());
		}
		String type = uriInfo.getQueryParameters().getFirst("type");
		filter.setType(type);

		String server = uriInfo.getQueryParameters().getFirst("server");
		try {
			if (!Strings.isNullOrEmpty(server)) {
				String uuids[] = server.split("/");
				server = uuids[uuids.length - 1];
				filter.setServer(server);
			}
		} catch (Exception e) {

		}

		String changesSince = uriInfo.getQueryParameters().getFirst("changes-since");
		if (!Strings.isNullOrEmpty(changesSince)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(FormatUtil.getDate(changesSince));
			filter.setChangesSince(calendar);
		}

		return filter;
	}
}
