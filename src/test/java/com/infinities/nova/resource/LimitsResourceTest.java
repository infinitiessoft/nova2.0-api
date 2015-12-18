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
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Singleton;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.GenericType;
//import javax.ws.rs.core.Response;
//
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.infinities.nova.api.EntityManagerFilter;
//import com.infinities.nova.api.VersionsApi;
//import com.infinities.nova.api.factory.VersionsApiFactory;
//import com.infinities.nova.api.middleware.ComputeReqIdMiddleware;
//import com.infinities.nova.api.middleware.NoAuthMiddleware;
//import com.infinities.nova.api.openstack.FaultWrapper;
//import com.infinities.nova.api.openstack.compute.limits.LimitsController;
//import com.infinities.nova.api.openstack.compute.limits.LimitsControllerFactory;
//import com.infinities.nova.api.openstack.compute.limits.LimitsTemplate;
//import com.infinities.nova.api.v2.Version2Api;
//import com.infinities.nova.api.v2.factory.Version2ApiFactory;
//import com.infinities.nova.response.model.Fault;
//import com.infinities.nova.response.model.Limits;
//import com.infinities.nova.response.model.Limits.AbsoluteLimit;
//import com.infinities.nova.response.model.Limits.RateLimit;
//import com.infinities.nova.util.jackson.JacksonFeature;
//import com.infinities.skyport.util.JsonUtil;
//
//public class LimitsResourceTest extends ResourceTest {
//
//	private final static Logger logger = LoggerFactory.getLogger(LimitsResourceTest.class);
//
//
//	@Test
//	public void testIndexForMalformedRequestURL() throws IOException {
//		Response response =
//				target("/v2").path("test").path("limits").register(JacksonFeature.class).request()
//						.header("X-Auth-Token", TOKEN).get();
//		// org.glassfish.jersey.filter.LoggingFilter
//		Map<String, Fault> faultData = response.readEntity(new GenericType<Map<String, Fault>>() {
//		});
//		String ret = JsonUtil.toString(faultData);
//		ResponseUtils.printResponse(logger, response);
//		logger.debug("{}", ret);
//		String key = faultData.keySet().iterator().next();
//		Fault fault = faultData.get(key);
//		assertEquals(400, response.getStatus());
//		assertEquals("badRequest", key);
//		assertEquals(response.getStatus(), fault.getCode());
//	}
//
//	@Test
//	public void testIndex() throws IOException {
//		Response response =
//				target("/v2").path("admin").path("limits").register(JacksonFeature.class).request()
//						.header("X-Auth-Token", TOKEN).get();
//		assertEquals(200, response.getStatus());
//		ResponseUtils.printResponse(logger, response);
//		LimitsTemplate wrapper = response.readEntity(LimitsTemplate.class);
//		String ret = JsonUtil.toString(wrapper);
//		logger.debug("{}", ret);
//		Limits limits = wrapper.getLimits();
//		List<RateLimit> rates = limits.getRate();
//		AbsoluteLimit absoluteLimit = limits.getAbsolute();
//		assertEquals(0, rates.size());
//		assertEquals(128, absoluteLimit.getMaxServerMeta().intValue());
//		// assertEquals(0, absoluteLimit.getServerMetaUsed().intValue());
//		assertEquals(5, absoluteLimit.getMaxPersonality().intValue());
//		assertEquals(128, absoluteLimit.getMaxImageMeta().intValue());
//		assertEquals(10240, absoluteLimit.getMaxPersonalitySize().intValue());
//		assertEquals(20, absoluteLimit.getMaxSecurityGroupRules().intValue());
//		assertEquals(100, absoluteLimit.getMaxTotalKeypairs().intValue());
//		// assertEquals(0, absoluteLimit.getTotalRAMUsed().intValue());
//		// assertEquals(0, absoluteLimit.getTotalInstancesUsed().intValue());
//		assertEquals(10, absoluteLimit.getMaxSecurityGroups().intValue());
//		// assertEquals(0, absoluteLimit.getTotalFloatingIpsUsed().intValue());
//		assertEquals(20, absoluteLimit.getMaxTotalCores().intValue());
//		// assertEquals(0,
//		// absoluteLimit.getTotalSecurityGroupsUsed().intValue());
//		assertEquals(10, absoluteLimit.getMaxTotalFloatingIps().intValue());
//		assertEquals(10, absoluteLimit.getMaxTotalInstances().intValue());
//		// assertEquals(0, absoluteLimit.getTotalCoresUsed().intValue());
//		assertEquals(51200, absoluteLimit.getMaxTotalRAMSize().intValue());
//
//	}
//
//
//	public static class NovaResourceTestApplication extends ResourceConfig {
//
//		public NovaResourceTestApplication() {
//			super(NovaResourceTestApplication.class);
//			this.register(new AbstractBinder() {
//
//				@Override
//				protected void configure() {
//					bindFactory(VersionsApiFactory.class).to(VersionsApi.class).in(Singleton.class);
//					bindFactory(Version2ApiFactory.class).to(Version2Api.class).in(Singleton.class);
//					bindFactory(LimitsControllerFactory.class).to(LimitsController.class);
//				}
//
//			});
//			this.register(FaultWrapper.class);
//			this.register(EntityManagerFilter.class);
//			this.register(ComputeReqIdMiddleware.class);
//			this.register(NoAuthMiddleware.class);
//			this.register(NovaResource.class);
//			this.register(JacksonFeature.class);
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
