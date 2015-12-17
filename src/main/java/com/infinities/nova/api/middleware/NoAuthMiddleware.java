package com.infinities.nova.api.middleware;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.common.Config;

@Priority(1005)
public class NoAuthMiddleware extends Middleware {

	private final static Logger logger = LoggerFactory.getLogger(NoAuthMiddleware.class);
	@Context
	HttpServletRequest httpRequest;


	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			baseCall(requestContext, true);
		} catch (Exception e) {
			logger.error("keystoneContext problem", e);
			throw new RuntimeException(e);
		}
	}

	protected void baseCall(ContainerRequestContext req, boolean projectIdInPath) throws Exception {
		if (!req.getHeaders().containsKey("X-Auth-Token")) {
			String userId = req.getHeaderString("X-Auth-User");
			if (Strings.isNullOrEmpty(userId)) {
				userId = "admin";
			}
			String projectId = req.getHeaderString("X-Auth-Project-Id");
			if (Strings.isNullOrEmpty(projectId)) {
				projectId = "admin";
			}
			String osUrl = null;
			if (projectIdInPath) {
				String url = StringUtils.removeEnd(req.getUriInfo().getRequestUri().toString(), "/");
				osUrl = Joiner.on("/").join(new String[] { url, projectId });
			} else {
				osUrl = StringUtils.removeEnd(req.getUriInfo().getRequestUri().toString(), "/");
			}
			Response res = Response.status(204).header("X-Auth-Token", String.format("%s:%s", userId, projectId))
					.header("X-Server-Management-Url", osUrl).type("text/plain").build();
			req.abortWith(res);
		}

		String token = req.getHeaderString("X-Auth-Token");
		String split[] = token.split(":");
		String projectId = split[0];
		String userId = split[0];
		if (split.length >= 3) {
			projectId = split[2];
		}
		String remoteAddress = httpRequest.getRemoteAddr();
		if (Strings.isNullOrEmpty(remoteAddress)) {
			remoteAddress = "127.0.0.1";
		}
		boolean useForwardedFor = Config.Instance.getOpt("use_forwarded_for").asBoolean();
		if (useForwardedFor) {
			remoteAddress = req.getHeaderString("X-Forwarded-For");
			if (Strings.isNullOrEmpty(remoteAddress)) {
				remoteAddress = "127.0.0.1";
			}
		}
		NovaRequestContext ctx = new NovaRequestContext(userId, projectId, true, "no", null, remoteAddress, null, null,
				null, true, null, null, null, null, false);

		req.setProperty("nova.context", ctx);

	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

	}
}
