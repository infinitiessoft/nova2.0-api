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
package com.infinities.nova.images.controller;

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
import com.infinities.api.openstack.commons.config.Config;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.AbstractPaginableController;
import com.infinities.nova.images.api.ImagesApi;
import com.infinities.nova.images.model.Image;
import com.infinities.nova.images.model.ImageTemplate;
import com.infinities.nova.images.model.ImagesTemplate;
import com.infinities.nova.images.model.MinimalImagesTemplate;
import com.infinities.nova.images.views.ViewBuilder;
import com.infinities.skyport.util.FormatUtil;

public class ImagesControllerImpl extends AbstractPaginableController implements ImagesController {

	private final static Logger logger = LoggerFactory.getLogger(ImagesControllerImpl.class);
	private final ViewBuilder builder;
	private final ImagesApi imageApi;


	public ImagesControllerImpl(Config config, ImagesApi imageApi) {
		super(config.getOpt("osapi_max_limit").asInteger());
		this.imageApi = imageApi;
		String osapiComputeLinkPrefix = config.getOpt("osapi_compute_link_prefix").asText();
		int osapiMaxLimit = config.getOpt("osapi_max_limit").asInteger();
		builder = new ViewBuilder(osapiComputeLinkPrefix, osapiMaxLimit);
	}

	@Override
	public MinimalImagesTemplate index(ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		ImagesFilter filters = getFilters(requestContext);
		PaginationParams pageParams = getPaginationParams(requestContext);

		List<Image> images = imageApi.getAll(context, filters, pageParams);
		return builder.index(requestContext, images);
	}

	@Override
	public ImagesTemplate detail(ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		ImagesFilter filters = getFilters(requestContext);
		PaginationParams pageParams = getPaginationParams(requestContext);

		List<Image> images = imageApi.getAll(context, filters, pageParams);
		// requestContext.cache_db_items("images", images, "id");
		return builder.detail(requestContext, images);
	}

	@Override
	public ImageTemplate show(String imageId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");

		Image image = imageApi.get(context, imageId);
		logger.debug("Successfully retrieved image {}", imageId);
		List<Image> images = new ArrayList<Image>();
		images.add(image);
		// requestContext.cache_db_items("image", images, "id");
		return builder.show(requestContext, image);

	}

	@Override
	public Response delete(String imageId, ContainerRequestContext requestContext) throws Exception {
		OpenstackRequestContext context = (OpenstackRequestContext) requestContext.getProperty("nova.context");
		imageApi.delete(context, imageId);
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
