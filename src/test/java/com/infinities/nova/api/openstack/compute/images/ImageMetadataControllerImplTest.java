//package com.infinities.nova.api.openstack.compute.images;
//
//import static junitparams.JUnitParamsRunner.$;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.core.MultivaluedHashMap;
//import javax.ws.rs.core.MultivaluedMap;
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
//import com.infinities.keystonemiddleware.model.Access.Service;
//import com.infinities.nova.api.NovaRequestContext;
//import com.infinities.nova.api.openstack.compute.images.api.ImagesApi;
//import com.infinities.nova.response.model.Meta;
//import com.infinities.nova.response.model.MetadataTemplate;
//
//@RunWith(JUnitParamsRunner.class)
//public class ImageMetadataControllerImplTest {
//
//	protected Mockery context = new JUnit4Mockery() {
//
//		{
//			setThreadingPolicy(new Synchroniser());
//			setImposteriser(ClassImposteriser.INSTANCE);
//		}
//	};
//	private ImageMetadataController controller;
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
//		controller = new ImageMetadataControllerImpl(api);
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
//		image.getProperties().put("key", "value");
//		image.getProperties().put("key1", "value1");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	@Parameters(method = "testIndexParams")
//	public void testIndex(String role) throws Exception {
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
//				oneOf(api).get(novaContext, "imageId", false);
//				will(returnValue(image));
//			}
//		});
//
//		MetadataTemplate metadata = controller.index("imageId", requestContext);
//		assertEquals(2, metadata.getMetadata().size());
//		assertTrue(metadata.getMetadata().containsKey("key"));
//		assertTrue(metadata.getMetadata().containsKey("key1"));
//		assertEquals("value", metadata.getMetadata().get("key"));
//		assertEquals("value1", metadata.getMetadata().get("key1"));
//	}
//
//	public Object[] testIndexParams() {
//		return $($("admin"), $("regular"));
//	}
//
//	@Test
//	@Parameters(method = "testShowParams")
//	public void testShow(String role, String key, String value, boolean expectExcetption) throws Exception {
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
//				oneOf(api).get(novaContext, "imageId", false);
//				will(returnValue(image));
//			}
//		});
//
//		if (!expectExcetption) {
//			Meta meta = controller.show("imageId", key, requestContext);
//			assertEquals(1, meta.getMeta().size());
//			assertTrue(meta.getMeta().containsKey(key));
//			assertEquals(value, meta.getMeta().get(key));
//		} else {
//			try {
//				controller.show("imageId", key, requestContext);
//				fail("didn't throw an exception!");
//			} catch (Exception e) {
//				e.printStackTrace();
//				// pass
//			}
//		}
//	}
//
//	public Object[] testShowParams() {
//		return $($("admin", "key", "value", false), $("admin", "key2", "value", true));
//	}
//
//	@Test
//	@Parameters(method = "testCreateParams")
//	public void testCreate(String role, String key, String value) throws Exception {
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
//				oneOf(api).get(novaContext, "imageId", false);
//				will(returnValue(image));
//
//				oneOf(api).update(novaContext, "imageId", image, true, null);
//				will(returnValue(image));
//			}
//		});
//
//		MetadataTemplate metadata = new MetadataTemplate();
//		metadata.setMetadata(new HashMap<String, String>());
//		metadata.getMetadata().put(key, value);
//		MetadataTemplate ret = controller.create("imageId", metadata, requestContext);
//		assertEquals(3, ret.getMetadata().size());
//	}
//
//	public Object[] testCreateParams() {
//		return $($("admin", "key2", "value2"));
//	}
//
//	@Test
//	@Parameters(method = "testUpdateParams")
//	public void testUpdate(String role, String key, String value) throws Exception {
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
//				oneOf(api).get(novaContext, "imageId", false);
//				will(returnValue(image));
//
//				oneOf(api).update(novaContext, "imageId", image, true, null);
//				will(returnValue(image));
//			}
//		});
//
//		Meta meta = new Meta();
//		meta.setMeta(new HashMap<String, String>());
//		meta.getMeta().put(key, value);
//		controller.update("imageId", key, meta, requestContext);
//	}
//
//	public Object[] testUpdateParams() {
//		return $($("admin", "key", "value2"));
//	}
//
//	@Test
//	@Parameters(method = "testDeleteParams")
//	public void testDelete(String role, String key) throws Exception {
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
//				oneOf(api).get(novaContext, "imageId", false);
//				will(returnValue(image));
//
//				oneOf(api).update(novaContext, "imageId", image, true, null);
//				will(returnValue(image));
//			}
//		});
//
//		controller.delete("imageId", key, requestContext);
//	}
//
//	public Object[] testDeleteParams() {
//		return $($("admin", "key"));
//	}
//
//	@Test
//	@Parameters(method = "testUpdataAllParams")
//	public void testUpdataAll(String role, String key, String value) throws Exception {
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
//				oneOf(api).get(novaContext, "imageId", false);
//				will(returnValue(image));
//
//				oneOf(api).update(novaContext, "imageId", image, true, null);
//				will(returnValue(image));
//			}
//		});
//
//		MetadataTemplate metadata = new MetadataTemplate();
//		metadata.setMetadata(new HashMap<String, String>());
//		metadata.getMetadata().put(key, value);
//		controller.updataAll("imageId", metadata, requestContext);
//	}
//	
//	public Object[] testUpdataAllParams() {
//		return $($("admin", "key","value"));
//	}
//
//}
