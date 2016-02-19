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
package com.infinities.nova.versions.v2.api;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.versions.api.VersionsApi;
import com.infinities.nova.versions.model.VersionWrapper;
import com.infinities.nova.versions.views.ViewBuilder;

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
