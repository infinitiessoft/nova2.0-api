package com.infinities.nova.openstack.common.policy.check;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.jmock.Expectations;
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

public class RoleCheckTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private Target target;
	private Credentials creds;
	private RoleCheck check;


	@Before
	public void setUp() throws Exception {
		enforcer = context.mock(Enforcer.class);
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		check = new RoleCheck();
		check.setKind("kind");
		check.setMatch("admin");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheck() {
		final Set<String> roles = new HashSet<String>();
		roles.add("admin");
		context.checking(new Expectations() {

			{
				oneOf(creds).getRoles();
				will(returnValue(roles));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck2() {
		final Set<String> roles = new HashSet<String>();
		roles.add("demo");
		context.checking(new Expectations() {

			{
				oneOf(creds).getRoles();
				will(returnValue(roles));
			}
		});
		assertFalse(check.check(target, creds, enforcer));
	}

}
