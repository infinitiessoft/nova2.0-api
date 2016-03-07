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
package com.infinities.nova.versions.views;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.infinities.api.openstack.commons.model.Link;
import com.infinities.api.openstack.commons.views.AbstractViewBuilder;
import com.infinities.nova.response.model.Version;
import com.infinities.nova.versions.model.VersionWrapper;
import com.infinities.nova.versions.model.VersionsWrapper;

public class ViewBuilder extends AbstractViewBuilder {

	private String baseUrl;


	public static ViewBuilder getViewBuilder(URI uri, String osapiComputeLinkPrefix) {
		return new ViewBuilder(uri.toString(), osapiComputeLinkPrefix);
	}

	private ViewBuilder(String baseUrl, String osapiComputeLinkPrefix) {
		super(osapiComputeLinkPrefix);
		this.baseUrl = baseUrl;
	}

	public VersionsWrapper buildChoices(Map<String, Version> versions, String requestPath) throws URISyntaxException {
		List<Version> versionObjs = new ArrayList<Version>();
		for (Version version : versions.values()) {
			Version proxy = new Version();
			proxy.setId(version.getId());
			proxy.setStatus(version.getStatus());
			Link link = new Link();
			link.setRel("self");
			link.setHref(generateHref(version.getId(), requestPath));
			proxy.getLinks().add(link);

			proxy.setMediaTypes(version.getMediaTypes());
			versionObjs.add(proxy);
		}
		VersionsWrapper wrapper = new VersionsWrapper(versionObjs);
		return wrapper;
	}

	public VersionsWrapper buildVersions(Map<String, Version> versions) throws URISyntaxException {
		List<Version> versionObjs = new ArrayList<Version>();

		for (Version version : versions.values()) {
			Version proxy = new Version();
			proxy.setId(version.getId());
			proxy.setStatus(version.getStatus());
			proxy.setUpdated(version.getUpdated());
			proxy.setLinks(buildLinks(version));
			proxy.setMediaTypes(null);
			versionObjs.add(proxy);
		}

		VersionsWrapper wrapper = new VersionsWrapper(versionObjs);
		return wrapper;
	}

	private String generateHref(String version, String path) throws URISyntaxException {
		String prefix = updateComputeLinkPrefix(new URI(this.baseUrl)).toString();
		String versionNumber = "";

		if (version.contains("v3.")) {
			versionNumber = "v3";
		} else {
			versionNumber = "v2";
		}

		if (Strings.isNullOrEmpty(path)) {
			return osPathJoin(prefix, versionNumber) + "/";
		} else {
			path = path.replaceAll("^/+|/+$", "");
			return osPathJoin(prefix, versionNumber, path);
		}
	}

	private List<Link> buildLinks(Version version) throws URISyntaxException {
		String href = this.generateHref(version.getId(), null);
		List<Link> links = new ArrayList<Link>(1);
		Link link = new Link();
		link.setRel("self");
		link.setHref(href);
		links.add(link);

		return links;
	}

	public VersionWrapper buildVersion(Version version) throws CloneNotSupportedException {
		Version reval = version.clone();
		Link link = new Link();
		link.setRel("self");
		String path = baseUrl.replaceAll("/+$", "") + "/";
		link.setHref(path);
		reval.getLinks().add(0, link);

		return new VersionWrapper(reval);
	}
}
