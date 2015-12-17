package com.infinities.nova.resource;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.infinities.nova.api.VersionsApi;
import com.infinities.nova.api.factory.VersionsApiFactory;
import com.infinities.nova.api.v2.Version2Api;
import com.infinities.nova.api.v2.factory.Version2ApiFactory;
import com.infinities.nova.model.wrapper.VersionListWrapper;
import com.infinities.nova.model.wrapper.VersionWrapper;
import com.infinities.nova.response.model.Link;
import com.infinities.nova.response.model.MediaType;
import com.infinities.nova.response.model.Version;
import com.infinities.nova.util.jackson.JacksonFeature;

public class NovaResourceTest extends ResourceTest {

	private static Version expected2;
	static {
		expected2 = new Version();
		expected2.setId("v2.0");
		expected2.setStatus("CURRENT");
		expected2.setUpdated("2011-01-21T11:33:21Z");
		Link link2 = new Link();
		link2.setRel("self");
		link2.setHref("http://localhost:9998/v2/");
		expected2.getLinks().add(link2);
	}


	@Test
	public void testGetApiV2Resource() {
		Response response = target("/v2").register(JacksonFeature.class).request().get();
		assertEquals(200, response.getStatus());
		VersionWrapper wrapper = response.readEntity(VersionWrapper.class);
		Version version = wrapper.getVersion();
		Version expected = new Version();
		expected.setId("v2.0");
		expected.setStatus("CURRENT");
		expected.setUpdated("2011-01-21T11:33:21Z");
		Link link = new Link();
		link.setRel("self");
		link.setHref("http://localhost:9998/v2/");
		expected.getLinks().add(link);

		Link link2 = new Link();
		link2.setRel("describedby");
		link2.setHref("http://doc.openstack.org/");
		link2.setType("text/html");
		expected.getLinks().add(link2);

		MediaType type2 = new MediaType();
		type2.setBase("application/json");
		type2.setType("application/vnd.openstack.compute+json;version=2");
		expected.getMediaTypes().add(type2);

		assertEquals(expected, version);
	}

	@Test
	public void testIndex() {
		Set<Version> versions = new HashSet<Version>();
		versions.add(expected2);

		Response response = target("/").register(JacksonFeature.class).request().get();
		assertEquals(200, response.getStatus());
		VersionListWrapper wrapper = response.readEntity(VersionListWrapper.class);
		assertEquals(1, wrapper.getVersions().size());
		assertEquals(versions, new HashSet<Version>(wrapper.getVersions()));
	}

	@Test
	public void testMulti() {
		Set<Version> versions = new HashSet<Version>();
		versions.add(expected2);

		Response response = target("").register(JacksonFeature.class).request().get();
		assertEquals(200, response.getStatus());
		VersionListWrapper wrapper = response.readEntity(VersionListWrapper.class);
		assertEquals(1, wrapper.getVersions().size());
		assertEquals(versions, new HashSet<Version>(wrapper.getVersions()));
	}


	public static class NovaResourceTestApplication extends ResourceConfig {

		public NovaResourceTestApplication() {

			this.register(new AbstractBinder() {

				@Override
				protected void configure() {
					bindFactory(VersionsApiFactory.class).to(VersionsApi.class).in(Singleton.class);
					bindFactory(Version2ApiFactory.class).to(Version2Api.class).in(Singleton.class);

				}

			});
			this.register(NovaResource.class);
			// this.register(VersionV3Resource.class);
			// this.register(VersionV2Resource.class);
			// this.register(ObjectMapperResolver.class);
			this.register(JacksonFeature.class);

		}

	}


	@Override
	protected Application getApplication() {
		return new NovaResourceTestApplication();
	}

}
