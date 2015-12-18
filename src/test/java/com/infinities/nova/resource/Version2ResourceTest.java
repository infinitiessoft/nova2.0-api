///*******************************************************************************
// * Copyright 2015 InfinitiesSoft Solutions Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may
// * not use this file except in compliance with the License. You may obtain
// * a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations
// * under the License.
// *******************************************************************************/
//package com.infinities.nova.resource;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.inject.Singleton;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.Response;
//
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.junit.Test;
//
//import com.infinities.nova.api.VersionsApi;
//import com.infinities.nova.api.factory.VersionsApiFactory;
//import com.infinities.nova.api.v2.Version2Api;
//import com.infinities.nova.api.v2.factory.Version2ApiFactory;
//import com.infinities.nova.model.wrapper.VersionWrapper;
//import com.infinities.nova.response.model.Link;
//import com.infinities.nova.response.model.MediaType;
//import com.infinities.nova.response.model.Version;
//import com.infinities.nova.util.jackson.JacksonFeature;
//
//public class Version2ResourceTest extends ResourceTest {
//
//	private static Version expected;
//	static {
//		expected = new Version();
//		expected.setId("v2.0");
//		expected.setStatus("CURRENT");
//		expected.setUpdated("2011-01-21T11:33:21Z");
//		Link link = new Link();
//		link.setRel("self");
//		link.setHref("http://localhost:9998/v2/");
//		expected.getLinks().add(link);
//		Link link2 = new Link();
//		link2.setRel("describedby");
//		link2.setHref("http://doc.openstack.org/");
//		link2.setType("text/html");
//		expected.getLinks().add(link2);
//
//	}
//
//
//	@Test
//	public void testGetVersion() {
//		Response response = target("/v2").register(JacksonFeature.class).request().get();
//		assertEquals(200, response.getStatus());
//		VersionWrapper wrapper = response.readEntity(VersionWrapper.class);
//		Version version = wrapper.getVersion();
//		Version expected = new Version();
//		expected.setId("v2.0");
//		expected.setStatus("CURRENT");
//		expected.setUpdated("2011-01-21T11:33:21Z");
//		Link link = new Link();
//		link.setRel("self");
//		link.setHref("http://localhost:9998/v2/");
//		expected.getLinks().add(link);
//
//		Link link2 = new Link();
//		link2.setRel("describedby");
//		link2.setHref("http://doc.openstack.org/");
//		link2.setType("text/html");
//		expected.getLinks().add(link2);
//
//		// MediaType type = new MediaType();
//		// type.setBase("application/xml");
//		// type.setType("application/vnd.openstack.compute+xml;version=2");
//		// expected.getMediaTypes().add(type);
//
//		MediaType type2 = new MediaType();
//		type2.setBase("application/json");
//		type2.setType("application/vnd.openstack.compute+json;version=2");
//		expected.getMediaTypes().add(type2);
//
//		assertEquals(expected, version);
//	}
//
//	@Test
//	public void testGetProjectMapperResource() {
//		Response response = target("/v2").path("test").register(JacksonFeature.class).request().get();
//		assertEquals(404, response.getStatus());
//	}
//
//
//	public static class NovaResourceTestApplication extends ResourceConfig {
//
//		public NovaResourceTestApplication() {
//
//			this.register(new AbstractBinder() {
//
//				@Override
//				protected void configure() {
//					bindFactory(VersionsApiFactory.class).to(VersionsApi.class).in(Singleton.class);
//					bindFactory(Version2ApiFactory.class).to(Version2Api.class).in(Singleton.class);
//
//				}
//
//			});
//			this.register(NovaResource.class);
//			// this.register(FaultWrapper.class);
//			// this.register(VersionV3Resource.class);
//			// this.register(VersionV2Resource.class);
//			// this.register(ObjectMapperResolver.class);
//			this.register(JacksonFeature.class);
//
//		}
//
//	}
//
//
//	@Override
//	protected Application getApplication() {
//		return new NovaResourceTestApplication();
//	}
//
// }
