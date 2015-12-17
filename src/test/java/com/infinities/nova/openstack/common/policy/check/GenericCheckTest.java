package com.infinities.nova.openstack.common.policy.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class GenericCheckTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private GenericCheck check;


	@Before
	public void setUp() throws Exception {
		enforcer = context.mock(Enforcer.class);
		check = new GenericCheck();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheck() {
		Target target = new MockTarget("test");
		Credentials creds = new MockCredentials("test");
		check.setKind("project_id");
		check.setMatch("%(project_id)s");
		assertTrue(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck2() {
		Target target = new MockTarget("test");
		Credentials creds = new MockCredentials("test");
		check.setKind("project_id");
		check.setMatch("%(user_id)s");
		assertTrue(check.check(target, creds, enforcer));
	}

	@Test
	public void testCheck3() {
		Target target = new MockTarget("test1");
		Credentials creds = new MockCredentials("test");
		check.setKind("project_id");
		check.setMatch("%(project_id)s");
		assertFalse(check.check(target, creds, enforcer));
	}

	@Test
	public void testCheck4() {
		Target target = new MockTarget("test");
		Credentials creds = new MockCredentials("test3");
		check.setKind("project_id");
		check.setMatch("%(project_id)s");
		assertFalse(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck5() {
		Target target = new MockTarget("test");
		Credentials creds = new MockCredentials("test");
		check.setKind("user_id");
		check.setMatch("%(project_id)s");
		assertTrue(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck6() {
		Target target = new MockTarget("test");
		Credentials creds = new MockCredentials("test");
		check.setKind("user_id");
		check.setMatch("%(user_id)s");
		assertTrue(check.check(target, creds, enforcer));
	}


	public static class MockTarget implements Target {

		private String project;


		public MockTarget(String project) {
			this.project = project;
		}

		public String getProjectId() {
			return project;
		}
		
		public String getUserId(){
			return project;
		}

		@Override
		public String getName() {
			return "mock target";
		}

	}

	public static class MockCredentials implements Credentials {

		private String project;


		public MockCredentials(String project) {
			this.project = project;
		}

		public String getProjectId() {
			return project;
		}
		
		public String getUserId(){
			return project;
		}

		@Override
		public boolean getIsAdmin() {
			return false;
		}

		@Override
		public Set<String> getRoles() {
			return new HashSet<String>();
		}

	}

}
