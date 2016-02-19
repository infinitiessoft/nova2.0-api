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
package com.infinities.nova.limits.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.limits.model.Limit;
import com.infinities.nova.limits.model.OverLimit;
import com.infinities.nova.limits.model.OverLimitWrapper;

public class RateLimitingMiddleware {

	private Limiter limiter;
	private List<Limit> DEFAULT_LIMITS;


	public RateLimitingMiddleware(String limits) throws CloneNotSupportedException, URISyntaxException {
		DEFAULT_LIMITS = Collections.unmodifiableList(Arrays.asList(new Limit("POST", new URI("*"), ".*", 120,
				TimeUnit.MINUTES), new Limit("POST", new URI("*/servers"), "^/servers", 120, TimeUnit.MINUTES), new Limit(
				"PUT", new URI("*"), ".*", 120, TimeUnit.MINUTES), new Limit("GET", new URI("*changes-since*"),
				".*changes-since.*", 120, TimeUnit.MINUTES), new Limit("DELETE", new URI("*"), ".*", 120, TimeUnit.MINUTES),
				new Limit("GET", new URI("*/os-fping"), "^/os-fping", 12, TimeUnit.MINUTES)));

		List<Limit> list = null;
		if (Strings.isNullOrEmpty(limits)) {
			list = Limiter.parseLimits(limits);
		}

		if (list == null || list.isEmpty()) {
			list = DEFAULT_LIMITS;
		}
		this.limiter = new Limiter(list);
	}

	public void call(ContainerRequestContext req) {
		String verb = req.getMethod();
		URI uri = req.getUriInfo().getRequestUri();
		NovaRequestContext context = (NovaRequestContext) req.getProperty("nova.context");

		String username;
		if (context != null) {
			username = context.getUserId();
		} else {
			username = null;
		}

		Entry<String, String> entry = limiter.checkForDelay(verb, uri, username);

		String delay = entry.getKey();

		if (!Strings.isNullOrEmpty(delay)) {
			int sec = Integer.parseInt(delay);
			Calendar retry = Calendar.getInstance();
			retry.add(Calendar.SECOND, sec);

			req.abortWith(new RateLimitFault("This request was rate-limited.", entry.getValue(), retry).call());
		}

		req.setProperty("nova.limits", limiter.getLimits(username));
	}


	private class RateLimitFault {

		private Response wrappedExec;


		private RateLimitFault(String message, String detail, Calendar retryTime) {
			long retryAfter = retryAfter(retryTime);
			OverLimit overLimit = new OverLimit(429, message, detail, retryAfter);
			OverLimitWrapper wrapper = new OverLimitWrapper();
			wrapper.setOverLimit(overLimit);

			wrappedExec = Response.status(429).header("title", "Too Many Requests").header("Retry-After", retryAfter)
					.entity(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
		}

		private Response call() {
			return wrappedExec;
		}

		private long retryAfter(Calendar retryTime) {
			long delay = retryTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
			delay = delay / 1000L;

			long retryAfter = delay > 0 ? delay : 0;
			return retryAfter;
		}

	}
}
