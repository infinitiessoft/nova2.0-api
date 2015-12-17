package com.infinities.nova.api.middleware;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.infinities.keystonemiddleware.model.Access;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.common.Config;
import com.infinities.skyport.util.JsonUtil;

public class NovaKeystoneContextMiddleware {

	private HttpServletRequest request;
	private final static Logger logger = LoggerFactory.getLogger(NovaKeystoneContextMiddleware.class);


	public void call(ContainerRequestContext env) throws Exception {
		logger.debug("NovaKeystoneContextMiddleware begin");
		String userId = env.getHeaderString("X-User-Id");
		if (Strings.isNullOrEmpty(userId)) {
			userId = env.getHeaderString("X-User");
		}

		if (Strings.isNullOrEmpty(userId)) {
			logger.debug("Neither X-User-Id nor X-User found in request");
			env.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}

		String[] roles = getRoles(env);
		logger.debug("Roles in headers: {}", new Object[] { roles });
		String projectId;

		if (env.getHeaders().containsKey("X-Tenant-Id")) {
			projectId = env.getHeaderString("X-Tenant-Id");
		} else {
			projectId = env.getHeaderString("X-Tenant");
		}

		String projectName = env.getHeaderString("X-Tenant-Name");
		String userName = env.getHeaderString("X-User-Name");

		String reqId = (String) env.getProperty(ComputeReqIdMiddleware.ENV_REQUEST_ID);

		logger.debug("projectId: {}, projectName: {}, userName: {}, requestId: {}", new Object[] { projectId, projectName,
				userName, reqId });

		String authToken = env.getHeaderString("X-Auth-Token");
		if (Strings.isNullOrEmpty(authToken)) {
			authToken = env.getHeaderString("X-Storage-Token");
		}

		String remoteAddress = null;
		remoteAddress = request.getRemoteAddr();
		if (Config.Instance.getOpt("use_forwarded_for").asBoolean()) {
			remoteAddress = env.getHeaderString("X-Forwarded-For");
			if (Strings.isNullOrEmpty(remoteAddress)) {
				remoteAddress = request.getRemoteAddr();
			}
		}
		logger.debug("remoteAddress: {}", new Object[] { remoteAddress });

		List<Access.Service> serviceCatalog = null;
		if (env.getHeaders().containsKey("X-Service-Catalog")) {
			try {
				String catalogHeader = env.getHeaderString("X-Service-Catalog");
				serviceCatalog = JsonUtil.readJson(catalogHeader, new TypeReference<List<Access.Service>>() {
				});
			} catch (Exception e) {
				env.abortWith(Response.status(Status.INTERNAL_SERVER_ERROR).entity("Invalid service catalog json").build());
			}
		}

		NovaRequestContext ctx = new NovaRequestContext(userId, projectId, null, "no", roles, remoteAddress, null, reqId,
				authToken, true, null, userName, projectName, serviceCatalog, false);
		env.setProperty("nova.context", ctx);
		logger.debug("NovaKeystoneContex end");
	}

	private String[] getRoles(ContainerRequestContext env) {
		String rolesStr;
		if (env.getHeaders().containsKey("X-Roles")) {
			rolesStr = env.getHeaderString("X-Roles");
		} else {
			rolesStr = env.getHeaderString("X-Role");
			if (Strings.isNullOrEmpty(rolesStr)) {
				rolesStr = "";
			}
			logger.warn("Sourcing roles from deprecated X-Role HTTP header");
		}
		String[] roles = rolesStr.split(",");
		for (int i = 0; i < roles.length; i++) {
			roles[i] = roles[i].trim();
		}
		return roles;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
