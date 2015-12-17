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
//package com.infinities.nova.api.openstack.compute.images.api.driver.jpa;
//
//import static junitparams.JUnitParamsRunner.$;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import junitparams.JUnitParamsRunner;
//import junitparams.Parameters;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.google.common.base.Strings;
//import com.infinities.keystonemiddleware.model.Access.Service;
//import com.infinities.nova.api.AbstractDbUnitTest;
//import com.infinities.nova.api.NovaRequestContext;
//import com.infinities.nova.api.openstack.compute.images.api.driver.ImagesDriver;
//import com.infinities.skyport.jpa.EntityManagerHelper;
//import com.infinities.skyport.util.FormatUtil;
//
//@RunWith(JUnitParamsRunner.class)
//public class JpaImagesDriverTest extends AbstractDbUnitTest {
//
//	private ImagesDriver driver;
//	private NovaRequestContext adminContext;
//
//
//	public JpaImagesDriverTest() {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		driver = new JpaImagesDriver();
//		adminContext = new NovaRequestContext("userId", "projectId", "userName", "projectName", new String[] { "admin" },
//				"authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	@Parameters(method = "testGetAllParams")
//	public void testGetAll(String name, String status, String containerFormat, String diskFormat, Long minRam, Long minDisk,
//			Long sizeMin, Long sizeMax, Calendar changesSince, Boolean protectedValue, Boolean deleted, Integer limit,
//			String sortKey, String sortDir, String marker, String memberStatus, Boolean adminAsUser, String visibility,
//			int size) throws Exception {
//		List<com.infinities.glance.model.Image> rets = driver.getAll(adminContext, name, status, containerFormat,
//				diskFormat, minRam, minDisk, sizeMin, sizeMax, changesSince, protectedValue, deleted, limit, sortKey,
//				sortDir, marker, memberStatus, adminAsUser, visibility);
//		assertEquals(size, rets.size());
//	}
//
//	public Object[] testGetAllParams() {
//		return $(
//				$(null, null, null, null, null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 4),
//				$("cirros-0.3.2-x86_64-disk", null, null, null, null, null, null, null, null, null, false, null, null, null,
//						null, null, null, null, 1),
//				$(null, "active", null, null, null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 4),
//				$(null, null, "bare", null, null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 3),
//				$(null, null, null, "vhd", null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 1),
//				$(null, null, null, null, 0L, null, null, null, null, null, false, null, null, null, null, null, null, null,
//						3),
//				$(null, null, null, null, null, 0L, null, null, null, null, false, null, null, null, null, null, null, null,
//						3),
//				$(null, null, null, null, null, null, 470000000L, null, null, null, false, null, null, null, null, null,
//						null, null, 3),
//				$(null, null, null, null, null, null, null, 470000000L, null, null, false, null, null, null, null, null,
//						null, null, 1),
//				$(null, null, null, null, null, null, null, null, FormatUtil.getCalendar("2014-05-04T17:15:16.000+08:00"),
//						null, false, null, null, null, null, null, null, null, 4),
//				$(null, null, null, null, null, null, null, null, null, true, false, null, null, null, null, null, null,
//						null, 0),
//				$(null, null, null, null, null, null, null, null, null, null, true, null, null, null, null, null, null,
//						null, 1),
//				$(null, null, null, null, null, null, null, null, null, null, false, 2, null, null, null, null, null, null,
//						2));
//	}
//
//	@Test
//	@Parameters(method = "testGetAllWithRegularUserParams")
//	public void testGetAllWithRegularUser(String name, String status, String containerFormat, String diskFormat,
//			Long minRam, Long minDisk, Long sizeMin, Long sizeMax, Calendar changesSince, Boolean protectedValue,
//			Boolean deleted, Integer limit, String sortKey, String sortDir, String marker, String memberStatus,
//			Boolean adminAsUser, String visibility, int size) throws Exception {
//		adminContext = new NovaRequestContext("userId", "5ef70662f8b34079a6eddb8da9d75fe8", "userName", "projectName",
//				new String[] { "regular" }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//		List<com.infinities.glance.model.Image> rets = driver.getAll(adminContext, name, status, containerFormat,
//				diskFormat, minRam, minDisk, sizeMin, sizeMax, changesSince, protectedValue, deleted, limit, sortKey,
//				sortDir, marker, memberStatus, adminAsUser, visibility);
//		assertEquals(size, rets.size());
//	}
//
//	public Object[] testGetAllWithRegularUserParams() {
//		return $(
//				$(null, null, null, null, null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 3),
//				$("cirros-0.3.2-x86_64-disk", null, null, null, null, null, null, null, null, null, false, null, null, null,
//						null, null, null, null, 1),
//				$(null, "active", null, null, null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 3),
//				$(null, null, "bare", null, null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 2),
//				$(null, null, null, "vhd", null, null, null, null, null, null, false, null, null, null, null, null, null,
//						null, 1),
//				$(null, null, null, null, 0L, null, null, null, null, null, false, null, null, null, null, null, null, null,
//						2),
//				$(null, null, null, null, null, 0L, null, null, null, null, false, null, null, null, null, null, null, null,
//						2),
//				$(null, null, null, null, null, null, 470000000L, null, null, null, false, null, null, null, null, null,
//						null, null, 2),
//				$(null, null, null, null, null, null, null, 470000000L, null, null, false, null, null, null, null, null,
//						null, null, 1),
//				$(null, null, null, null, null, null, null, null, FormatUtil.getCalendar("2014-05-04T17:15:16.000+08:00"),
//						null, false, null, null, null, null, null, null, null, 3),
//				$(null, null, null, null, null, null, null, null, null, true, false, null, null, null, null, null, null,
//						null, 0),
//				$(null, null, null, null, null, null, null, null, null, null, true, null, null, null, null, null, null,
//						null, 1),
//				$(null, null, null, null, null, null, null, null, null, null, false, 2, null, null, null, null, null, null,
//						2));
//	}
//
//	@Test
//	@Parameters(method = "testGetWithRegularUserParams")
//	public void testGetWithRegularUser(String imageId, boolean showDeleted, boolean expectExcetption) throws Exception {
//		adminContext = new NovaRequestContext("userId", "5ef70662f8b34079a6eddb8da9d75fe8", "userName", "projectName",
//				new String[] { "regular" }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//		if (!expectExcetption) {
//			com.infinities.glance.model.Image ret = driver.get(adminContext, imageId, showDeleted);
//			assertEquals(imageId, ret.getId());
//		} else {
//			try {
//				driver.get(adminContext, imageId, showDeleted);
//				fail("didn't throw an exception!");
//			} catch (Exception e) {
//				// pass
//			}
//		}
//	}
//
//	public Object[] testGetWithRegularUserParams() {
//		return $($(null, false, true), $("781b3762-9469-4cec-b58d-3349e5de4e9c", false, false),
//				$("681b3762-9469-4cec-b58d-3349e5de4e2d", false, true),
//				$("681b3762-9469-4cec-b58d-3349e5de4e2d", true, true),
//				$("781b3762-9469-4cec-b58d-3349e5de4e2d", false, true));
//	}
//
//	@Test
//	@Parameters(method = "testGetParams")
//	public void testGet(String imageId, boolean showDeleted, boolean expectExcetption) throws Exception {
//		if (!expectExcetption) {
//			com.infinities.glance.model.Image ret = driver.get(adminContext, imageId, showDeleted);
//			assertEquals(imageId, ret.getId());
//		} else {
//			try {
//				driver.get(adminContext, imageId, showDeleted);
//				fail("didn't throw an exception!");
//			} catch (Exception e) {
//				// pass
//			}
//		}
//	}
//
//	public Object[] testGetParams() {
//		return $($(null, false, true), $("781b3762-9469-4cec-b58d-3349e5de4e9c", false, false),
//				$("681b3762-9469-4cec-b58d-3349e5de4e2d", false, true),
//				$("681b3762-9469-4cec-b58d-3349e5de4e2d", true, false),
//				$("781b3762-9469-4cec-b58d-3349e5de4e2d", false, false));
//	}
//
//	@Test
//	@Parameters(method = "testDestroyWithRegularUserParams")
//	public void testDestroyWithRegularUser(String imageId, boolean expectExcetption) throws Exception {
//		adminContext = new NovaRequestContext("userId", "5ef70662f8b34079a6eddb8da9d75fe8", "userName", "projectName",
//				new String[] { "regular" }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//		try {
//			if (!expectExcetption) {
//				driver.destroy(adminContext, imageId);
//			} else {
//				try {
//					driver.destroy(adminContext, imageId);
//					fail("didn't throw an exception!");
//				} catch (Exception e) {
//					// pass
//				}
//			}
//		} finally {
//			EntityManagerHelper.commitAndClose();
//		}
//	}
//
//	public Object[] testDestroyWithRegularUserParams() {
//		return $($(null, true), $("781b3762-9469-4cec-b58d-3349e5de4e9c", true),
//				$("781b3762-9469-4cec-b58d-3349e5de4e9d", false), $("681b3762-9469-4cec-b58d-3349e5de4e2d", true),
//				$("781b3762-9469-4cec-b58d-3349e5de4e2d", true));
//	}
//
//	@Test
//	@Parameters(method = "testDestroyParams")
//	public void testDestroy(String imageId, boolean expectExcetption) throws Exception {
//		try {
//			if (!expectExcetption) {
//				driver.destroy(adminContext, imageId);
//			} else {
//				try {
//					driver.destroy(adminContext, imageId);
//					fail("didn't throw an exception!");
//				} catch (Exception e) {
//					e.printStackTrace();
//					// pass
//				}
//			}
//		} finally {
//			EntityManagerHelper.commitAndClose();
//		}
//
//	}
//
//	public Object[] testDestroyParams() {
//		return $($(null, true), $("781b3762-9469-4cec-b58d-3349e5de4e9c", false),
//				$("781b3762-9469-4cec-b58d-3349e5de4e9d", false), $("681b3762-9469-4cec-b58d-3349e5de4e2d", true),
//				$("781b3762-9469-4cec-b58d-3349e5de4e2d", false));
//	}
//
//	@Test
//	@Parameters(method = "testUpdateParams")
//	public void testUpdate(String imageId, boolean purgeProps, String fromState, String name, String status,
//			String containerFormat, String diskFormat, Integer minRam, Integer minDisk, Long size, Boolean protectedValue,
//			Boolean isPublic, String key, String value) throws Exception {
//		try {
//			com.infinities.glance.model.Image values = new com.infinities.glance.model.Image();
//			values.setName(name);
//			values.setStatus(status);
//			values.setContainerFormat(containerFormat);
//			values.setDiskFormat(diskFormat);
//			values.setMinRam(minRam);
//			values.setMinDisk(minDisk);
//			values.setSize(size);
//			values.setProtected(protectedValue);
//			values.setPublic(isPublic);
//			if (!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(value)) {
//				values.getProperties().put(key, value);
//			}
//
//			com.infinities.glance.model.Image ret = driver.update(adminContext, imageId, values, purgeProps, fromState);
//			if (!Strings.isNullOrEmpty(name))
//				assertEquals(values.getName(), ret.getName());
//			if (!Strings.isNullOrEmpty(status))
//				assertEquals(values.getStatus(), ret.getStatus());
//			if (!Strings.isNullOrEmpty(containerFormat))
//				assertEquals(values.getContainerFormat(), ret.getContainerFormat());
//			if (!Strings.isNullOrEmpty(diskFormat))
//				assertEquals(values.getDiskFormat(), ret.getDiskFormat());
//			if (minRam != null)
//				assertEquals(values.getMinRam(), ret.getMinRam());
//			if (minDisk != null)
//				assertEquals(values.getMinDisk(), ret.getMinDisk());
//			if (size != null)
//				assertEquals(values.getSize(), ret.getSize());
//			if (protectedValue != null)
//				assertEquals(values.isProtected(), ret.isProtected());
//			if (isPublic != null)
//				assertEquals(values.isPublic(), ret.isPublic());
//
//			if (!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(value)) {
//				String v = ret.getProperties().get(key);
//				assertEquals(value, v);
//			}
//
//		} finally {
//			EntityManagerHelper.commitAndClose();
//		}
//	}
//
//	public Object[] testUpdateParams() {
//		return $(
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, null, null, null, false,
//						null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, "Ubuntu 10.04 Plain 5GB", null, null, null, null,
//						null, null, null, false, null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, "queued", null, null, null, null, null, null,
//						false, null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, "ovf", null, null, null, null, null,
//						false, null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, "vhd", null, null, null, null,
//						false, null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, 1, null, null, null, false,
//						null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, 1, null, null, false,
//						null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, null, 470000000L, null,
//						false, null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, null, null, null, false,
//						null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, null, null, true, false,
//						null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, null, null, null, true,
//						null, null),
//				$("1bea47ed-f6a9-463b-b423-14b9cca9ad27", true, null, null, null, null, null, null, null, null, null, true,
//						"key1", "value1"));
//	}
//
//	@Test
//	@Parameters(method = "testGetAndUpdatePropertiesParams")
//	public void testGetAndUpdateProperties(String imageId, String key, String value) throws Exception {
//		com.infinities.glance.model.Image ret = driver.get(adminContext, imageId, false);
//		String v = ret.getProperties().get(key);
//		assertNotNull(v);
//		assertEquals("val", v);
//		System.err.println(v);
//
//		com.infinities.glance.model.Image values = new com.infinities.glance.model.Image();
//		values.getProperties().put(key, value);
//
//		ret = driver.update(adminContext, imageId, values, true, null);
//		String v2 = ret.getProperties().get(key);
//		assertNotNull(v2);
//		assertEquals(value, v2);
//		System.err.println(v2);
//	}
//
//	public Object[] testGetAndUpdatePropertiesParams() {
//		return $($("1bea47ed-f6a9-463b-b423-14b9cca9ad27", "test", "value1"));
//	}
//
//	@Test
//	@Parameters(method = "testGetAndDeletePropertiesParams")
//	public void testGetAndDeleteProperties(String imageId) throws Exception {
//		com.infinities.glance.model.Image ret = driver.get(adminContext, imageId, false);
//		assertEquals(2, ret.getProperties().size());
//
//		com.infinities.glance.model.Image values = new com.infinities.glance.model.Image();
//
//		ret = driver.update(adminContext, imageId, values, true, null);
//		assertEquals(0, ret.getProperties().size());
//	}
//
//	public Object[] testGetAndDeletePropertiesParams() {
//		return $($("1bea47ed-f6a9-463b-b423-14b9cca9ad27"));
//	}
//
//}
