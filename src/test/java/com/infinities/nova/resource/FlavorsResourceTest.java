package com.infinities.nova.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.api.EntityManagerFilter;
import com.infinities.nova.api.VersionsApi;
import com.infinities.nova.api.factory.VersionsApiFactory;
import com.infinities.nova.api.middleware.ComputeReqIdMiddleware;
import com.infinities.nova.api.middleware.NoAuthMiddleware;
import com.infinities.nova.api.openstack.FaultWrapper;
import com.infinities.nova.api.openstack.compute.flavors.FlavorTemplate;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsController;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsControllerFactory;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsTemplate;
import com.infinities.nova.api.openstack.compute.flavors.MinimalFlavor;
import com.infinities.nova.api.openstack.compute.flavors.MinimalFlavorsTemplate;
import com.infinities.nova.api.openstack.compute.flavors.api.FlavorsApi;
import com.infinities.nova.api.openstack.compute.flavors.api.FlavorsApiFactory;
import com.infinities.nova.api.v2.Version2Api;
import com.infinities.nova.api.v2.factory.Version2ApiFactory;
import com.infinities.nova.response.model.Fault;
import com.infinities.nova.util.jackson.JacksonFeature;
import com.infinities.skyport.util.JsonUtil;

public class FlavorsResourceTest extends ResourceTest {

	private static final Logger logger = LoggerFactory.getLogger(FlavorsResourceTest.class);


	@Test
	public void testIndexForMalformedRequestURL() throws IOException {
		Response response = target("/v2").path("test").path("flavors").register(JacksonFeature.class).request()
				.header("X-Auth-Token", TOKEN).get();
		// org.glassfish.jersey.filter.LoggingFilter
		Map<String, Fault> faultData = response.readEntity(new GenericType<Map<String, Fault>>() {
		});
		String ret = JsonUtil.toString(faultData);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		String key = faultData.keySet().iterator().next();
		Fault fault = faultData.get(key);
		assertEquals(400, response.getStatus());
		assertEquals("badRequest", key);
		assertEquals(response.getStatus(), fault.getCode());
	}

	@Test
	public void testIndex() throws IOException {
		Response response = target("/v2").path("admin").path("flavors").register(JacksonFeature.class).request()
				.header("X-Auth-Token", TOKEN).get();
		assertEquals(200, response.getStatus());
		ResponseUtils.printResponse(logger, response);
		MinimalFlavorsTemplate flavors = response.readEntity(MinimalFlavorsTemplate.class);
		String ret = JsonUtil.toString(flavors);
		logger.debug("{}", ret);
		List<MinimalFlavor> flavorList = flavors.getList();
		assertEquals(5, flavorList.size());
		for (MinimalFlavor flavor : flavorList) {
			assertNotNull(flavor.getId());
			assertNotNull(flavor.getName());
			assertFalse(flavor.getLinks().isEmpty());
		}
	}

	@Test
	public void testDetail() throws IOException {
		Response response = target("/v2").path("admin").path("flavors").path("detail").register(JacksonFeature.class)
				.request().header("X-Auth-Token", TOKEN).get();
		assertEquals(200, response.getStatus());
		ResponseUtils.printResponse(logger, response);
		FlavorsTemplate flavors = response.readEntity(FlavorsTemplate.class);
		String ret = JsonUtil.toString(flavors);
		logger.debug("{}", ret);
		List<com.infinities.nova.api.openstack.compute.flavors.Flavor> flavorList = flavors.getList();
		assertEquals(5, flavorList.size());
		for (com.infinities.nova.api.openstack.compute.flavors.Flavor flavor : flavorList) {
			assertNotNull(flavor.getId());
			assertNotNull(flavor.getName());
			assertNotNull(flavor.getRam());
			assertNotNull(flavor.getVcpus());
			assertNotNull(flavor.getDisk());
			assertFalse(flavor.getLinks().isEmpty());
		}

	}

	@Test
	public void testShow() throws IOException {
		Response response = target("/v2").path("admin").path("flavors").path("1").register(JacksonFeature.class).request()
				.header("X-Auth-Token", TOKEN).get();
		assertEquals(200, response.getStatus());
		ResponseUtils.printResponse(logger, response);
		FlavorTemplate template = response.readEntity(FlavorTemplate.class);
		String ret = JsonUtil.toString(template);
		com.infinities.nova.api.openstack.compute.flavors.Flavor flavor = template.getFlavor();
		logger.debug("{}", ret);
		assertEquals("1", flavor.getId());
		assertEquals("m1.tiny", flavor.getName());
		assertEquals(512, flavor.getRam().intValue());
		assertEquals(1, flavor.getVcpus().intValue());
		assertEquals(1, flavor.getDisk().intValue());
		assertEquals(2, flavor.getLinks().size());
	}


	public static class NovaResourceTestApplication extends ResourceConfig {

		public NovaResourceTestApplication() {
			super(NovaResourceTestApplication.class);

			this.register(new AbstractBinder() {

				@Override
				protected void configure() {
					bindFactory(VersionsApiFactory.class).to(VersionsApi.class).in(Singleton.class);
					bindFactory(Version2ApiFactory.class).to(Version2Api.class).in(Singleton.class);
					bindFactory(FlavorsApiFactory.class).to(FlavorsApi.class).in(Singleton.class);
					bindFactory(FlavorsControllerFactory.class).to(FlavorsController.class).in(Singleton.class);
					// bindFactory(QuotaHomeFactory.class).to(QuotaHome.class);
				}

			});
			this.register(FaultWrapper.class);
			this.register(EntityManagerFilter.class);
			this.register(ComputeReqIdMiddleware.class);
			this.register(NoAuthMiddleware.class);
			this.register(NovaResource.class);
			this.register(JacksonFeature.class);
		}

	}


	@Override
	protected Application getApplication() {
		return new NovaResourceTestApplication();
	}

}
