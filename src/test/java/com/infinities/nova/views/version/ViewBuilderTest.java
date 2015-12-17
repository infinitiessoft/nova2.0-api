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
package com.infinities.nova.views.version;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.model.wrapper.IdentityVersionListWrapper;
import com.infinities.nova.model.wrapper.VersionWrapper;
import com.infinities.nova.response.model.Link;
import com.infinities.nova.response.model.MediaType;
import com.infinities.nova.response.model.Version;

public class ViewBuilderTest {

	private ViewBuilder builder;
	private String baseUrl = "http://localhost";
	private Map<String, Version> map = new HashMap<String, Version>();


	@Before
	public void setUp() throws Exception {
		builder = ViewBuilder.getViewBuilder(new URI(baseUrl));
		Version version = new Version();
		version.setId("id");
		version.setStatus("status");
		version.setUpdated("updated");
		Link link = new Link();
		link.setHref("href");
		link.setRel("rel");
		link.setType("type");
		version.getLinks().add(link);
		MediaType type = new MediaType();
		type.setBase("base");
		type.setType("type");
		version.getMediaTypes().add(type);

		Version version2 = new Version();
		version2.setId("id2");
		version2.setStatus("status");
		version2.setUpdated("updated");
		Link link2 = new Link();
		link2.setHref("href");
		link2.setRel("rel");
		link2.setType("type");
		version2.getLinks().add(link2);
		MediaType type2 = new MediaType();
		type2.setBase("base");
		type2.setType("type");
		version2.getMediaTypes().add(type2);

		map.put("id", version);
		map.put("id2", version2);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildChoices() throws URISyntaxException, CloneNotSupportedException {
		String requestPath = "/skyport/";
		Set<Version> versions = new HashSet<Version>();
		for (Version v : map.values()) {
			Version clone = v.clone();
			Link link = new Link();
			link.setRel("self");
			link.setHref(baseUrl + "/" + "v2" + requestPath.subSequence(0, requestPath.length() - 1));
			clone.getLinks().clear();
			clone.getLinks().add(link);
			clone.setUpdated(null);
			versions.add(clone);
		}

		IdentityVersionListWrapper wrapper = builder.buildChoices(map, requestPath);
		Collection<Version> rets = wrapper.getValues();
		assertEquals(versions, new HashSet<Version>(rets));
	}

	@Test
	public void testBuildVersions() throws URISyntaxException, CloneNotSupportedException {
		Set<Version> versions = new HashSet<Version>();
		for (Version v : map.values()) {
			Version clone = v.clone();
			Link link = new Link();
			link.setRel("self");
			link.setHref(baseUrl + "/" + "v2" + "/");
			clone.getLinks().clear();
			clone.getLinks().add(link);
			clone.getMediaTypes().clear();
			versions.add(clone);
		}
		IdentityVersionListWrapper wrapper = builder.buildVersions(map);
		assertEquals(2, wrapper.getValues().size());
	}

	@Test
	public void testBuildVersion() throws CloneNotSupportedException {
		Version version = new Version();
		version.setId("id");
		version.setStatus("status");
		version.setUpdated("updated");
		Link link = new Link();
		link.setHref("href");
		link.setRel("rel");
		link.setType("type");
		version.getLinks().add(link);
		MediaType type = new MediaType();
		type.setBase("base");
		type.setType("type");
		version.getMediaTypes().add(type);
		Version clone = version.clone();
		Link link2 = new Link();
		link2.setRel("self");
		link2.setHref(baseUrl + "/");
		clone.getLinks().add(0, link2);

		VersionWrapper wrapper = builder.buildVersion(version);
		Version ret = wrapper.getVersion();
		assertEquals(clone, ret);
	}

	@Test
	public void testBuildVersion2() throws CloneNotSupportedException {
		baseUrl = "http://localhost/";
		Version version = new Version();
		version.setId("id");
		version.setStatus("status");
		version.setUpdated("updated");
		Link link = new Link();
		link.setHref("href");
		link.setRel("rel");
		link.setType("type");
		version.getLinks().add(link);
		MediaType type = new MediaType();
		type.setBase("base");
		type.setType("type");
		version.getMediaTypes().add(type);
		Version clone = version.clone();
		Link link2 = new Link();
		link2.setRel("self");
		link2.setHref(baseUrl);
		clone.getLinks().add(0, link2);

		VersionWrapper wrapper = builder.buildVersion(version);
		Version ret = wrapper.getVersion();
		assertEquals(clone, ret);
	}

}
