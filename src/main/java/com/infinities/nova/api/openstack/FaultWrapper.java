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
package com.infinities.nova.api.openstack;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.api.exception.SafeException;

public class FaultWrapper implements ExceptionMapper<SafeException> {

	private final static Logger logger = LoggerFactory.getLogger(FaultWrapper.class);


	@Override
	public Response toResponse(SafeException inner) {
		logger.error("Cauht Error", inner);

		boolean safe = false;
		Map<String, List<Object>> headers = null;
		int status = 500;

		safe = inner.isSafe();
		headers = inner.getHeaders();
		status = inner.getCode();

		ResponseBuilder res = Response.status(status);
		if (headers != null) {
			for (Entry<String, List<Object>> entry : headers.entrySet()) {
				for (Object obj : entry.getValue()) {
					res.header(entry.getKey(), obj);
				}
			}
		}
		logger.debug("inner:{}", inner.getMessage());

		WebApplicationException outer = null;

		if (safe) {
			outer = new WebApplicationException(String.format("%s:%s", inner.getClass().getName(), inner.getMessage()),
					res.build());
		} else {
			Response response = res.build();
			String message = Strings.isNullOrEmpty(inner.getMessage()) ? response.getStatusInfo().getReasonPhrase() : inner
					.getMessage();
			outer = new WebApplicationException(message, response);
		}

		return new FaultException(outer);
	}
}
