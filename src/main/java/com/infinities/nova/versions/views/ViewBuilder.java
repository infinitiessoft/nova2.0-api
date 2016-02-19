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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.infinities.nova.common.config.Config;
import com.infinities.nova.response.model.Link;
import com.infinities.nova.response.model.Version;
import com.infinities.nova.versions.model.VersionWrapper;
import com.infinities.nova.versions.model.VersionsWrapper;

public class ViewBuilder {

	private String baseUrl;


	public static ViewBuilder getViewBuilder(URI uri) {
		return new ViewBuilder(uri.toString());
	}

	private ViewBuilder(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public VersionsWrapper buildChoices(Map<String, Version> versions, String requestPath) throws URISyntaxException {
		List<Version> versionObjs = new ArrayList<Version>();
		for (Version version : versions.values()) {
			Version proxy = new Version();
			proxy.setId(version.getId());
			proxy.setStatus(version.getStatus());
			com.infinities.nova.response.model.Link link = new com.infinities.nova.response.model.Link();
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
		String prefix = updateComputeLinkPrefix(this.baseUrl);
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

	private String osPathJoin(String... strings) {
		checkNotNull(strings);
		checkArgument(strings.length > 0);
		final CharMatcher joinCharMatcher = CharMatcher.is('/');
		return Joiner.on('/').join(Iterables.transform(Arrays.asList(strings), new Function<String, String>() {

			@Override
			public String apply(final String input) {
				return joinCharMatcher.trimFrom(input);
			}
		}));
	}

	private String updateComputeLinkPrefix(String origUrl) throws URISyntaxException {
		return updateLinkPrefix(origUrl, Config.Instance.getOpt("osapi_compute_link_prefix").asText());
	}

	private String updateLinkPrefix(String origUrl, String prefix) throws URISyntaxException {
		if (Strings.isNullOrEmpty(prefix)) {
			return origUrl;
		}

		URI prefixParts = new URI(prefix);
		URI urlParts =
				UriBuilder.fromUri(origUrl).scheme(prefixParts.getScheme()).host(prefixParts.getHost())
						.port(prefixParts.getPort()).replacePath(prefixParts.getPath()).build();
		return urlParts.toString();
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
