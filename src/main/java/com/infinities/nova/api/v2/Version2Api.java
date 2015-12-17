package com.infinities.nova.api.v2;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.api.VersionsApi;
import com.infinities.nova.model.wrapper.VersionWrapper;
import com.infinities.nova.views.version.ViewBuilder;

public class Version2Api {

	private Logger logger = LoggerFactory.getLogger(Version2Api.class);


	public Response show(URI uri) {
		// @extensions.expected_error(404)
		try {
			ViewBuilder builder = ViewBuilder.getViewBuilder(uri);
			VersionWrapper versionWrapper = builder.buildVersion(VersionsApi.VERSIONS.get("v2.0"));
			return Response.ok(versionWrapper).build();
		} catch (Throwable t) {
			logger.error("error in VersionApi", t);
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	// @Override
	// public String getName() {
	// return "Versions";
	// }
	//
	// @Override
	// public String getAlias() {
	// return "Versions";
	// }

}
