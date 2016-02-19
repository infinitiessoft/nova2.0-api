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
package com.infinities.nova.versions.api;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.infinities.nova.response.model.CustomResponseStatus;
import com.infinities.nova.response.model.MediaType;
import com.infinities.nova.response.model.Version;
import com.infinities.nova.versions.model.VersionsWrapper;
import com.infinities.nova.versions.views.ViewBuilder;

public class VersionsApi {

	public final static Map<String, Link> LINKS = new HashMap<String, Link>(2);
	public final static Map<String, Version> VERSIONS = new HashMap<String, Version>(2);

	static {
		LINKS.put("v2.0", new Link("http://doc.openstack.org/"));
		// LINKS.put("v3.0", new Link("http://doc.openstack.org/"));
		Version v2 = new Version();
		v2.setId("v2.0");
		v2.setStatus("CURRENT");
		v2.setUpdated("2011-01-21T11:33:21Z");
		com.infinities.nova.response.model.Link linkV2 = new com.infinities.nova.response.model.Link();
		linkV2.setRel("describedby");
		linkV2.setType("text/html");
		linkV2.setHref(LINKS.get("v2.0").getHtml());
		v2.getLinks().add(linkV2);
		// MediaType mediaTypeV2 = new MediaType();
		// mediaTypeV2.setBase("application/xml");
		// mediaTypeV2.setType("application/vnd.openstack.compute+xml;version=2");
		// v2.getMediaTypes().add(mediaTypeV2);
		MediaType mediaTypeV2Json = new MediaType();
		mediaTypeV2Json.setBase("application/json");
		mediaTypeV2Json.setType("application/vnd.openstack.compute+json;version=2");
		v2.getMediaTypes().add(mediaTypeV2Json);
		VERSIONS.put("v2.0", v2);
		// Version v3 = new Version();
		// v3.setId("v3.0");
		// v3.setStatus("EXPERIMENTAL");
		// v3.setUpdated("2013-07-23T11:33:21Z");
		// com.infinities.nova.model.Link link = new
		// com.infinities.nova.model.Link();
		// link.setRel("describedby");
		// link.setType("text/html");
		// link.setHref(LINKS.get("v3.0").getHtml());
		// v3.getLinks().add(link);
		// MediaType mediaType = new MediaType();
		// mediaType.setBase("application/json");
		// mediaType.setType("application/vnd.openstack.compute+json;version=3");
		// v3.getMediaTypes().add(mediaType);
		// VERSIONS.put("v3.0", v3);
	}


	// status 300
	public Response multi(URI uri, String requestPath) throws URISyntaxException {
		ViewBuilder builder = ViewBuilder.getViewBuilder(uri);
		VersionsWrapper versionsWrapper = builder.buildChoices(VERSIONS, requestPath);
		return Response.status(CustomResponseStatus.MULTIPLE_CHOICES).entity(versionsWrapper).build();
	}

	// status 200
	public Response index(URI uri) throws URISyntaxException {
		ViewBuilder builder = ViewBuilder.getViewBuilder(uri);
		VersionsWrapper versionsWrapper = builder.buildVersions(VERSIONS);
		return Response.ok().entity(versionsWrapper).build();
	}


	public final static class Link implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String html;


		public Link() {

		}

		public Link(String html) {
			this.html = html;
		}

		public void setHtml(String html) {
			this.html = html;
		}

		public String getHtml() {
			return html;
		}

	}

}
