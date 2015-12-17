//package com.infinities.nova.api.openstack.compute.images;
//
//import static junitparams.JUnitParamsRunner.$;
//import static org.junit.Assert.assertEquals;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.core.MultivaluedHashMap;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import junitparams.JUnitParamsRunner;
//import junitparams.Parameters;
//
//import org.jmock.Expectations;
//import org.jmock.Mockery;
//import org.jmock.integration.junit4.JUnit4Mockery;
//import org.jmock.lib.concurrent.Synchroniser;
//import org.jmock.lib.legacy.ClassImposteriser;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.google.common.base.Strings;
//import com.infinities.keystonemiddleware.model.Access.Service;
//import com.infinities.nova.api.NovaRequestContext;
//import com.infinities.nova.api.openstack.compute.images.api.ImagesApi;
//import com.infinities.nova.model.wrapper.ImageWrapper;
//import com.infinities.nova.response.model.Images;
//import com.infinities.skyport.util.FormatUtil;
//
//@RunWith(JUnitParamsRunner.class)
//public class ImagesControllerImplTest {
//
//	protected Mockery context = new JUnit4Mockery() {
//
//		{
//			setThreadingPolicy(new Synchroniser());
//			setImposteriser(ClassImposteriser.INSTANCE);
//		}
//	};
//	private ImagesController controller;
//	private ImagesApi api;
//	private ContainerRequestContext requestContext;
//	private com.infinities.glance.model.Image image;
//	private UriInfo uriInfo;
//	private URI uri;
//
//
//	@Before
//	public void setUp() throws Exception {
//		api = context.mock(ImagesApi.class);
//		controller = new ImagesControllerImpl(api);
//		requestContext = context.mock(ContainerRequestContext.class);
//		uriInfo = context.mock(UriInfo.class);
//		uri = new URI("http://localhost:8085/v2/projectId/images");
//		final MultivaluedMap<String, String> queryParam = new MultivaluedHashMap<String, String>();
//		context.checking(new Expectations() {
//
//			{
//				allowing(uriInfo).getRequestUri();
//				will(returnValue(uri));
//
//				allowing(uriInfo).getQueryParameters();
//				will(returnValue(queryParam));
//
//				allowing(requestContext).getUriInfo();
//				will(returnValue(uriInfo));
//			}
//		});
//
//		image = new com.infinities.glance.model.Image();
//		image.setChecksum("checksum");
//		image.setContainerFormat("containerFormat");
//		image.setCreatedAt(Calendar.getInstance());
//		image.setDeleted(false);
//		image.setDeletedAt(Calendar.getInstance());
//		image.setDiskFormat("diskFormat");
//		image.setId("id");
//		image.setMinDisk(0);
//		image.setMinRam(0);
//		image.setName("name");
//		image.setOwner("owner");
//		image.setProtected(false);
//		image.setPublic(true);
//		image.setSize(0L);
//		image.setStatus("active");
//		image.setUpdatedAt(Calendar.getInstance());
//		image.setVirtualSize(0L);
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	@Parameters(method = "testIndexParams")
//	public void testIndex(final String name, final String status, final String containerFormat, final String diskFormat,
//			final Long minRam, final Long minDisk, final Long sizeMin, final Long sizeMax, final String changesSince,
//			final Boolean protectedValue, final Integer deleted, final Integer limit, final String sortKey,
//			final String sortDir, String isPublic, final String marker, final String role) throws Exception {
//		final NovaRequestContext novaContext = new NovaRequestContext("userId", "projectId", "userName", "projectName",
//				new String[] { role }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//
//		final String memberStatus = "all";
//
//		// Boolean isPublicBool = true;
//		final Boolean adminAsUser = "admin".equals(role) ? true : null;
//		final String visibility = null;
//		final Calendar calendar = !Strings.isNullOrEmpty(changesSince) ? Calendar.getInstance() : null;
//		if (!Strings.isNullOrEmpty(changesSince)) {
//			calendar.setTime(FormatUtil.getDate(changesSince));
//		}
//
//		Boolean temp = null;
//		if (novaContext.getIsAdmin()) {
//			if (deleted != null) {
//				if (deleted == 1) {
//					temp = true;
//				} else {
//					temp = false;
//				}
//			} else if (Strings.isNullOrEmpty(changesSince)) {
//				temp = false;
//			}
//		}
//
//		final Boolean deletedBool = temp;
//
//		final List<com.infinities.glance.model.Image> imageList = new ArrayList<com.infinities.glance.model.Image>();
//		imageList.add(image);
//
//		context.checking(new Expectations() {
//
//			{
//				allowing(requestContext).getProperty("nova.context");
//				will(returnValue(novaContext));
//
//				oneOf(requestContext).getUriInfo();
//				will(returnValue(uriInfo));
//
//				oneOf(api).getAll(novaContext, name, status, containerFormat, diskFormat, minRam, minDisk, sizeMin, sizeMax,
//						calendar, protectedValue, deletedBool, limit == null ? 25 : limit, sortKey, sortDir, marker,
//						memberStatus, adminAsUser, visibility);
//				will(returnValue(imageList));
//			}
//		});
//
//		Images images = controller.index(requestContext, name, status, containerFormat, diskFormat, minRam, minDisk,
//				sizeMin, sizeMax, changesSince, protectedValue, deleted, limit, sortKey, sortDir, isPublic, marker);
//
//		assertEquals(1, images.getList().size());
//		com.infinities.nova.response.model.Image ret = images.getList().get(0);
//		assertEquals(image.getId(), ret.getId());
//		// assertEquals(null, ret.getStatus());
//		assertEquals(image.getName(), ret.getName());
//		// assertEquals(image.getMinRam(), ret.getMinRam());
//		// assertEquals(image.getMinDisk(), ret.getMinDisk());
//		// assertEquals(image.getCreatedAt(), ret.getCreated());
//		// assertEquals(image.getUpdatedAt(), ret.getUpdated());
//		// assertEquals(image.getSize(), ret.getSize());
//	}
//
//	public Object[] testIndexParams() {
//		return $(
//				$(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "admin"),
//				$("name", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "admin"),
//				$(null, "status", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
//						"admin"),
//				$(null, null, "containerFormat", null, null, null, null, null, null, null, null, null, null, null, null,
//						null, "admin"),
//				$(null, null, null, "diskFormat", null, null, null, null, null, null, null, null, null, null, null, null,
//						"admin"),
//				$(null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, null, null, "admin"),
//				$(null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, null, "admin"),
//				$(null, null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, "admin"),
//				$(null, null, null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, "admin"),
//				$(null, null, null, null, null, null, null, null, "2014-05-04T17:15:16.000+08:00", null, null, null, null,
//						null, null, null, "admin"),
//				$(null, null, null, null, null, null, null, null, null, true, null, null, null, null, null, null, "admin"),
//				$(null, null, null, null, null, null, null, null, null, null, 0, null, null, null, null, null, "admin"),
//				$(null, null, null, null, null, null, null, null, null, null, null, 0, null, null, null, null, "admin"),
//				$(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "regular"),
//				$("name", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
//						"regular"),
//				$(null, "status", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
//						"regular"),
//				$(null, null, "containerFormat", null, null, null, null, null, null, null, null, null, null, null, null,
//						null, "regular"),
//				$(null, null, null, "diskFormat", null, null, null, null, null, null, null, null, null, null, null, null,
//						"regular"),
//				$(null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, null, null, "regular"),
//				$(null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, null, "regular"),
//				$(null, null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, null, "regular"),
//				$(null, null, null, null, null, null, null, 0L, null, null, null, null, null, null, null, null, "regular"),
//				$(null, null, null, null, null, null, null, null, "2014-05-04T17:15:16.000+08:00", null, null, null, null,
//						null, null, null, "regular"),
//				$(null, null, null, null, null, null, null, null, null, true, null, null, null, null, null, null, "regular"),
//				$(null, null, null, null, null, null, null, null, null, null, 0, null, null, null, null, null, "regular"),
//				$(null, null, null, null, null, null, null, null, null, null, null, 0, null, null, null, null, "regular"));
//	}
//
//	@Test
//	@Parameters(method = "testIndexParams")
//	public void testDetail(final String name, final String status, final String containerFormat, final String diskFormat,
//			final Long minRam, final Long minDisk, final Long sizeMin, final Long sizeMax, final String changesSince,
//			final Boolean protectedValue, final Integer deleted, final Integer limit, final String sortKey,
//			final String sortDir, String isPublic, final String marker, final String role) throws Exception {
//		final NovaRequestContext novaContext = new NovaRequestContext("userId", "projectId", "userName", "projectName",
//				new String[] { role }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//
//		final String memberStatus = "all";
//
//		// Boolean isPublicBool = true;
//		final Boolean adminAsUser = "admin".equals(role) ? true : null;
//		final String visibility = null;
//		final Calendar calendar = !Strings.isNullOrEmpty(changesSince) ? Calendar.getInstance() : null;
//		if (!Strings.isNullOrEmpty(changesSince)) {
//			calendar.setTime(FormatUtil.getDate(changesSince));
//		}
//
//		Boolean temp = null;
//		if (novaContext.getIsAdmin()) {
//			if (deleted != null) {
//				if (deleted == 1) {
//					temp = true;
//				} else {
//					temp = false;
//				}
//			} else if (Strings.isNullOrEmpty(changesSince)) {
//				temp = false;
//			}
//		}
//
//		final Boolean deletedBool = temp;
//
//		final List<com.infinities.glance.model.Image> imageList = new ArrayList<com.infinities.glance.model.Image>();
//		imageList.add(image);
//
//		context.checking(new Expectations() {
//
//			{
//				allowing(requestContext).getProperty("nova.context");
//				will(returnValue(novaContext));
//
//				oneOf(requestContext).getUriInfo();
//				will(returnValue(uriInfo));
//
//				oneOf(api).getAll(novaContext, name, status, containerFormat, diskFormat, minRam, minDisk, sizeMin, sizeMax,
//						calendar, protectedValue, deletedBool, limit == null ? 25 : limit, sortKey, sortDir, marker,
//						memberStatus, adminAsUser, visibility);
//				will(returnValue(imageList));
//			}
//		});
//
//		Images images = controller.detail(requestContext, name, status, containerFormat, diskFormat, minRam, minDisk,
//				sizeMin, sizeMax, changesSince, protectedValue, deleted, limit, sortKey, sortDir, isPublic, marker);
//
//		assertEquals(1, images.getList().size());
//		com.infinities.nova.response.model.Image ret = images.getList().get(0);
//		assertEquals(image.getId(), ret.getId());
//		assertEquals(image.getStatus().toUpperCase(), ret.getStatus());
//		assertEquals(image.getName(), ret.getName());
//		assertEquals(image.getMinRam(), ret.getMinRam());
//		assertEquals(image.getMinDisk(), ret.getMinDisk());
//		assertEquals(image.getCreatedAt(), ret.getCreated());
//		assertEquals(image.getUpdatedAt(), ret.getUpdated());
//		// assertEquals(image.getSize(), ret.getSize());
//	}
//
//	@Test
//	@Parameters(method = "testShowParams")
//	public void testShow(final String imageId, String role) throws Exception {
//		final NovaRequestContext novaContext = new NovaRequestContext("userId", "projectId", "userName", "projectName",
//				new String[] { role }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//
//		context.checking(new Expectations() {
//
//			{
//				allowing(requestContext).getProperty("nova.context");
//				will(returnValue(novaContext));
//
//				oneOf(requestContext).getUriInfo();
//				will(returnValue(uriInfo));
//
//				oneOf(api).get(novaContext, imageId, false);
//				will(returnValue(image));
//			}
//		});
//
//		ImageWrapper wrapper = controller.show(imageId, requestContext);
//
//		com.infinities.nova.response.model.Image ret = wrapper.getImage();
//		assertEquals(image.getId(), ret.getId());
//		assertEquals(image.getStatus().toUpperCase(), ret.getStatus());
//		assertEquals(image.getName(), ret.getName());
//		assertEquals(image.getMinRam(), ret.getMinRam());
//		assertEquals(image.getMinDisk(), ret.getMinDisk());
//		assertEquals(image.getCreatedAt(), ret.getCreated());
//		assertEquals(image.getUpdatedAt(), ret.getUpdated());
//	}
//
//	public Object[] testShowParams() {
//		return $($("imageId", "admin"), $("imageId", "regular"));
//	}
//
//	@Test
//	@Parameters(method = "testDeleteParams")
//	public void testDelete(final String imageId, String role) throws Exception {
//		final NovaRequestContext novaContext = new NovaRequestContext("userId", "projectId", "userName", "projectName",
//				new String[] { role }, "authToken", "remoteAddress", true, new ArrayList<Service>(), "reqId");
//
//		context.checking(new Expectations() {
//
//			{
//				allowing(requestContext).getProperty("nova.context");
//				will(returnValue(novaContext));
//
//				oneOf(requestContext).getUriInfo();
//				will(returnValue(uriInfo));
//
//				oneOf(api).destroy(novaContext, imageId);
//			}
//		});
//
//		Response response = controller.delete(imageId, requestContext);
//		assertEquals(204, response.getStatus());
//	}
//
//	public Object[] testDeleteParams() {
//		return $($("imageId", "admin"), $("imageId", "regular"));
//	}
//
//}
