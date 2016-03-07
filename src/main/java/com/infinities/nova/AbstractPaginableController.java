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
package com.infinities.nova;

import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import jersey.repackaged.com.google.common.collect.Maps;

import com.google.common.base.Strings;
import com.infinities.api.openstack.commons.exception.http.HTTPBadRequestException;

/**
 * @author pohsun
 *
 */
public class AbstractPaginableController {

	private int osapiMaxLimit;


	public AbstractPaginableController(int osapiMaxLimit) {
		this.osapiMaxLimit = osapiMaxLimit;
	}

	protected Entry<Integer, String> getLimitAndMarker(ContainerRequestContext request) {
		return getLimitAndMarker(request, null);
	}

	protected Entry<Integer, String> getLimitAndMarker(ContainerRequestContext request, String maxLimitStr) {
		PaginationParams params = getPaginationParams(request);
		Integer maxLimit;
		if (Strings.isNullOrEmpty(maxLimitStr)) {
			maxLimit = osapiMaxLimit;
		} else {
			maxLimit = Integer.parseInt(maxLimitStr);
		}

		int limit = params.getLimit() != null ? params.getLimit() : osapiMaxLimit;
		limit = Math.min(maxLimit, limit);
		String marker = params.getMarker();
		return Maps.immutableEntry(maxLimit, marker);
	}

	protected PaginationParams getPaginationParams(ContainerRequestContext requestContext) {
		PaginationParams params = new PaginationParams();
		MultivaluedMap<String, String> parameters = requestContext.getUriInfo().getQueryParameters();

		if (parameters.containsKey("limit")) {
			params.setLimit(getIntParam(parameters.getFirst("limit"), "limit"));
		}
		if (parameters.containsKey("page_size")) {
			params.setPageSize(getIntParam(parameters.getFirst("page_size"), "page_size"));
		}
		if (parameters.containsKey("marker")) {
			params.setMarker(getMarkerParam(requestContext));
		}

		return params;
	}

	private String getMarkerParam(ContainerRequestContext requestContext) {
		return requestContext.getUriInfo().getQueryParameters().getFirst("marker");
	}

	private int getIntParam(String first, String param) {
		int intParam;
		try {
			intParam = Integer.parseInt(first);
		} catch (Exception e) {
			String msg = String.format("%s param must be an integer", param);
			throw new HTTPBadRequestException(msg);
		}

		if (intParam < 0) {
			String msg = String.format("%s param must be positive", param);
			throw new HTTPBadRequestException(msg);
		}

		return intParam;
	}


	public static class PaginationParams {

		private Integer limit;
		private Integer pageSize;
		private String marker;


		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}

		public String getMarker() {
			return marker;
		}

		public void setMarker(String marker) {
			this.marker = marker;
		}

	}

}
