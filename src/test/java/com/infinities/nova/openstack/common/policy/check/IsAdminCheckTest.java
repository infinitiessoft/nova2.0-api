package com.infinities.nova.openstack.common.policy.check;

import static org.junit.Assert.*;

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

public class IsAdminCheckTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private Target target;
	private Credentials creds;
	private IsAdminCheck check;


	@Before
	public void setUp() throws Exception {
		enforcer = context.mock(Enforcer.class);
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		check = new IsAdminCheck();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheck() {
		check.setKind("kind");
		check.setMatch("true");
		context.checking(new Expectations() {

			{
				oneOf(creds).getIsAdmin();
				will(returnValue(true));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck2() {
		check.setKind("kind");
		check.setMatch("false");
		context.checking(new Expectations() {

			{
				oneOf(creds).getIsAdmin();
				will(returnValue(false));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck3() {
		check.setKind("kind");
		check.setMatch("false");
		context.checking(new Expectations() {

			{
				oneOf(creds).getIsAdmin();
				will(returnValue(true));
			}
		});
		assertFalse(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck4() {
		check.setKind("kind");
		check.setMatch("true");
		context.checking(new Expectations() {

			{
				oneOf(creds).getIsAdmin();
				will(returnValue(false));
			}
		});
		assertFalse(check.check(target, creds, enforcer));
	}

}
