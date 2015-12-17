package com.infinities.nova.api.openstack.wsgi;

import javax.ws.rs.container.ContainerRequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.exception.http.HTTPBadRequestException;

public class Resource {

	private final static Logger logger = LoggerFactory.getLogger(Resource.class);


	public static void processStack(ContainerRequestContext requestContext, String projectid, NovaRequestContext context) {
		logger.debug("projectid: {}, context projectid: {}", new Object[] { projectid, context.getProjectId() });
		if (!Strings.isNullOrEmpty(projectid) && context != null && !projectid.equals(context.getProjectId())) {
			String msg = String.format(
					"Malformed request URL: URL's project_id '%s' doesn't match Context's project_id '%s'", projectid,
					context.getProjectId());

			throw new HTTPBadRequestException(msg);
		}
	}
}
