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
package com.infinities.nova.limits;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.limits.model.LimitWrapper;
import com.infinities.nova.limits.model.LimitsTemplate;
import com.infinities.nova.limits.views.ViewBuilder;
import com.infinities.nova.quota.QuotaEngine;
import com.infinities.nova.quota.QuotaUsageSet;

public class LimitsControllerImpl implements LimitsController {

	private final QuotaEngine quotas;


	// private final Logger logger =
	// LoggerFactory.getLogger(LimitsControllerImpl.class);

	public LimitsControllerImpl() {
		quotas = QuotaEngine.getQUOTAS();
	}

	@Override
	public LimitsTemplate index(ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		String projectid = requestContext.getUriInfo().getQueryParameters().getFirst("tenant_id");
		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}
		QuotaUsageSet absLimits = quotas.getProjectQuotas(context, projectid, null, true, false, false);

		@SuppressWarnings("unchecked")
		List<LimitWrapper> rateLimits = (List<LimitWrapper>) requestContext.getProperty("nova.limits");
		if (rateLimits == null) {
			rateLimits = new ArrayList<LimitWrapper>();
		}

		ViewBuilder builder = getViewBuilder(requestContext);
		return builder.build(rateLimits, absLimits);
	}

	private ViewBuilder getViewBuilder(ContainerRequestContext requestContext) {
		return new ViewBuilder();
	}
}
