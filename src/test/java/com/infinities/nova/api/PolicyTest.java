package com.infinities.nova.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.keystonemiddleware.model.Access.Service;
import com.infinities.keystonemiddleware.model.Access.Service.Endpoint;
import com.infinities.keystonemiddleware.model.Link;
import com.infinities.nova.openstack.common.policy.Target;

public class PolicyTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private final String userId = "userId";
	private final String projectId = "projectId";
	private final String userName = "userName";
	private final String projectName = "projectName";
	private String[] roles = new String[] { "role" };
	private final String authToken = "authToken";
	private final String remoteAddress = "remoteAddress";
	private List<Service> serviceCatalog;
	private final String reqId = "reqId";
	private Service service;
	private Endpoint endpoint;
	private Link link;
	private NovaRequestContext requestContext;
	private Target target;


	@Before
	public void setUp() throws Exception {
		serviceCatalog = new ArrayList<Service>();
		service = new Service();
		service.setName("name");
		service.setType("type");
		endpoint = new Endpoint();
		endpoint.setAdminURL("adminURL");
		endpoint.setInternalURL("internalURL");
		endpoint.setPublicURL("publicURL");
		endpoint.setRegion("region");
		service.getEndpoints().add(endpoint);
		link = new Link();
		link.setHref("href");
		link.setRel("rel");
		link.setType("type");
		service.getEndpointsLinks().add(link);
		serviceCatalog.add(service);
		requestContext = new NovaRequestContext(userId, projectId, null, "no", roles, remoteAddress, null, reqId, authToken,
				true, null, userName, projectName, serviceCatalog, false);

		target = context.mock(Target.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheckIsAdmin() throws Exception {
		assertFalse(Policy.checkIsAdmin(requestContext));
	}

	@Test
	public void testCheckIsAdmin2() throws Exception {
		roles = new String[] { "admin" };
		requestContext = new NovaRequestContext(userId, projectId, null, "no", roles, remoteAddress, null, reqId, authToken,
				true, null, userName, projectName, serviceCatalog, false);
		assertTrue(Policy.checkIsAdmin(requestContext));
	}

	@Test
	public void testEnforce() throws Exception {
		String action = "network:delete_dns_domain";
		assertTrue(Policy.enforce(requestContext, action, target, false, new IllegalStateException("test")));
	}

	@Test
	public void testEnforce2() throws Exception {
		String action = "context_is_admin";
		assertFalse(Policy.enforce(requestContext, action, target, false, new IllegalStateException("test")));
	}

	@Test(expected = IllegalStateException.class)
	public void testEnforce3() throws Exception {
		String action = "context_is_admin";
		assertFalse(Policy.enforce(requestContext, action, target, true, new IllegalStateException("test")));
	}

	@Test
	public void testEnforce4() throws Exception {
		String action = "context_is_admin";
		roles = new String[] { "admin" };
		requestContext = new NovaRequestContext(userId, projectId, null, "no", roles, remoteAddress, null, reqId, authToken,
				true, null, userName, projectName, serviceCatalog, false);
		assertTrue(Policy.enforce(requestContext, action, target, true, new IllegalStateException("test")));
	}
}
