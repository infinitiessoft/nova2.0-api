package com.infinities.nova.api.openstack.compute.limits;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.quota.QuotaEngine;
import com.infinities.nova.quota.QuotaUsageSet;
import com.infinities.nova.views.limits.ViewBuilder;

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
