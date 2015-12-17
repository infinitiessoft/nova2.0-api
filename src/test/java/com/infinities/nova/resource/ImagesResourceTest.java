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
package com.infinities.nova.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
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
import com.infinities.nova.api.openstack.common.template.MetaItemTemplate;
import com.infinities.nova.api.openstack.common.template.MetadataTemplate;
import com.infinities.nova.api.openstack.compute.images.ImageMetadataController;
import com.infinities.nova.api.openstack.compute.images.ImageMetadataControllerFactory;
import com.infinities.nova.api.openstack.compute.images.ImageTemplate;
import com.infinities.nova.api.openstack.compute.images.ImagesController;
import com.infinities.nova.api.openstack.compute.images.ImagesControllerFactory;
import com.infinities.nova.api.openstack.compute.images.ImagesTemplate;
import com.infinities.nova.api.openstack.compute.images.MinimalImage;
import com.infinities.nova.api.openstack.compute.images.MinimalImagesTemplate;
import com.infinities.nova.api.openstack.compute.images.api.ImageMetadataApi;
import com.infinities.nova.api.openstack.compute.images.api.ImageMetadataApiFactory;
import com.infinities.nova.api.openstack.compute.images.api.ImagesApi;
import com.infinities.nova.api.openstack.compute.images.api.ImagesApiFactory;
import com.infinities.nova.api.openstack.compute.images.api.driver.ImagesDriver;
import com.infinities.nova.api.openstack.compute.images.api.driver.ImagesDriverFactory;
import com.infinities.nova.api.v2.Version2Api;
import com.infinities.nova.api.v2.factory.Version2ApiFactory;
import com.infinities.nova.response.model.Fault;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.util.jackson.JacksonFeature;
import com.infinities.skyport.util.JsonUtil;

public class ImagesResourceTest extends ResourceTest {

	private final static Logger logger = LoggerFactory.getLogger(ImagesResourceTest.class);


	@Test
	public void testIndexForMalformedRequestURL() throws IOException {
		Response response = target("/v2").path("test").path("images").register(JacksonFeature.class).request()
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
	public void testCreate() throws IOException {
		Response response = target("/v2").path("test").path("images").register(JacksonFeature.class).request()
				.header("X-Auth-Token", TOKEN).post(null);
		Map<String, Fault> faultData = response.readEntity(new GenericType<Map<String, Fault>>() {
		});
		String ret = JsonUtil.toString(faultData);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		String key = faultData.keySet().iterator().next();
		Fault fault = faultData.get(key);
		assertEquals(405, response.getStatus());
		assertEquals("badMethod", key);
		assertEquals(response.getStatus(), fault.getCode());
	}

	@Test
	public void testIndex() throws IOException {
		Response response = target("/v2").path("admin").path("images").register(JacksonFeature.class).request()
				.header("X-Auth-Token", TOKEN).get();
		assertEquals(200, response.getStatus());
		MinimalImagesTemplate images = response.readEntity(MinimalImagesTemplate.class);
		List<MinimalImage> imageList = images.getList();
		ResponseUtils.printResponse(logger, response);
		String ret = JsonUtil.toString(images);
		logger.debug("{}", ret);
		assertEquals(2, imageList.size());
		for (MinimalImage image : imageList) {
			assertNotNull(image.getId());
			assertNotNull(image.getName());
			assertFalse(image.getLinks().isEmpty());
		}
	}

	@Test
	public void testDatail() throws IOException {
		Response response = target("/v2").path("admin").path("images").path("detail").register(JacksonFeature.class)
				.request().header("X-Auth-Token", TOKEN).get();
		assertEquals(200, response.getStatus());
		String ret = response.readEntity(String.class);
		ImagesTemplate images = JsonUtil.readJson(ret, ImagesTemplate.class);
		List<Image> imageList = images.getList();
		logger.debug("images size:{}", images.getList().size());
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(2, imageList.size());
	}

	@Test
	public void testShow() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).register(JacksonFeature.class)
				.request().header("X-Auth-Token", TOKEN).get();
		assertEquals(200, response.getStatus());
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		ImageTemplate template = JsonUtil.readJson(ret, ImageTemplate.class);
		Image image = template.getImage();
		assertEquals(imageId, image.getId());
	}

	@Test
	public void testDelete() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).register(JacksonFeature.class)
				.request().header("X-Auth-Token", TOKEN).delete();
		ResponseUtils.printResponse(logger, response);
		assertEquals(204, response.getStatus());
		// ImageWrapper wrapper = response.readEntity(ImageWrapper.class);
		// Image image = wrapper.getImage();
		// assertEquals("1bea47ed-f6a9-463b-b423-14b9cca9ad27", image.getId());
		// String ret = JsonUtil.convertValue(wrapper);
		// System.err.println(ret);
	}

	@Test
	public void testIndexMetadatasNotFound() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadatas")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN).get();
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(404, response.getStatus());
		assertTrue(ret.startsWith("<html>"));
	}

	@Test
	public void testIndexMetadata() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN).get();
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(200, response.getStatus());
		MetadataTemplate metadata = JsonUtil.readJson(ret, MetadataTemplate.class);
		Map<String, String> map = metadata.getMetadata();
		assertEquals(2, map.size());
	}

	@Test
	public void testShowMetadataCouldNotFound() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata").path("test3")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN).get();
		Map<String, Fault> faultData = response.readEntity(new GenericType<Map<String, Fault>>() {
		});
		String ret = JsonUtil.toString(faultData);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		String key = faultData.keySet().iterator().next();
		Fault fault = faultData.get(key);
		assertEquals(404, response.getStatus());
		assertEquals("itemNotFound", key);
		assertEquals(response.getStatus(), fault.getCode());
	}

	@Test
	public void testShowMetadata() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata").path("test")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN).get();
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(200, response.getStatus());
		MetaItemTemplate metadata = JsonUtil.readJson(ret, MetaItemTemplate.class);
		Map<String, String> map = metadata.getMeta();
		assertEquals(1, map.size());
	}

	@Test
	public void testPostMetadata() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		MetadataTemplate input = new MetadataTemplate();
		input.setMetadata(new HashMap<String, String>());
		input.getMetadata().put("test3", "val3");
		input.getMetadata().put("test4", "val4");
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN)
				.post(Entity.entity(input, MediaType.APPLICATION_JSON_TYPE));
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(200, response.getStatus());
		MetadataTemplate metadata = JsonUtil.readJson(ret, MetadataTemplate.class);
		Map<String, String> map = metadata.getMetadata();
		assertEquals(4, map.size());
	}

	@Test
	public void testUpdateMetadata() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		MetaItemTemplate input = new MetaItemTemplate();
		input.setMeta(new HashMap<String, String>());
		input.getMeta().put("test2", "val3");
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata").path("test2")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN)
				.put(Entity.entity(input, MediaType.APPLICATION_JSON_TYPE));
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(200, response.getStatus());
		MetaItemTemplate meta = JsonUtil.readJson(ret, MetaItemTemplate.class);
		Map<String, String> map = meta.getMeta();
		assertEquals(1, map.size());
		assertEquals("val3", map.get("test2"));
	}

	@Test
	public void testUpdateAllMetadata() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		MetadataTemplate input = new MetadataTemplate();
		input.setMetadata(new HashMap<String, String>());
		input.getMetadata().put("test3", "val3");
		input.getMetadata().put("test4", "val4");
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN)
				.put(Entity.entity(input, MediaType.APPLICATION_JSON_TYPE));
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(200, response.getStatus());
		MetadataTemplate metadata = JsonUtil.readJson(ret, MetadataTemplate.class);
		Map<String, String> map = metadata.getMetadata();
		assertEquals(2, map.size());
		assertEquals("val3", map.get("test3"));
		assertEquals("val4", map.get("test4"));
	}

	@Test
	public void testDeleteMetadata() throws IOException {
		String imageId = "c2e5db72bd7fd153f53ede5da5a06de3";
		Response response = target("/v2").path("admin").path("images").path(imageId).path("metadata").path("test")
				.register(JacksonFeature.class).request().header("X-Auth-Token", TOKEN).delete();
		String ret = response.readEntity(String.class);
		ResponseUtils.printResponse(logger, response);
		logger.debug("{}", ret);
		assertEquals(204, response.getStatus());
	}


	public static class NovaResourceTestApplication extends ResourceConfig {

		public NovaResourceTestApplication() {
			super(NovaResourceTestApplication.class);

			this.register(new AbstractBinder() {

				@Override
				protected void configure() {
					bindFactory(VersionsApiFactory.class).to(VersionsApi.class).in(Singleton.class);
					bindFactory(Version2ApiFactory.class).to(Version2Api.class).in(Singleton.class);
					bindFactory(ImagesDriverFactory.class).to(ImagesDriver.class);
					bindFactory(ImagesApiFactory.class).to(ImagesApi.class);
					bindFactory(ImagesControllerFactory.class).to(ImagesController.class);
					bindFactory(ImageMetadataControllerFactory.class).to(ImageMetadataController.class);
					bindFactory(ImageMetadataApiFactory.class).to(ImageMetadataApi.class);
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
