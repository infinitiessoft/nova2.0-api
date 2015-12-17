package com.infinities.nova.openstack.common.policy.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class NotCheckTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private Target target;
	private Credentials creds;
	private NotCheck check;
	private BaseCheck baseCheck;


	@Before
	public void setUp() throws Exception {
		enforcer = context.mock(Enforcer.class);
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		baseCheck = context.mock(BaseCheck.class);
		check = new NotCheck(baseCheck);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheck() {
		context.checking(new Expectations() {

			{
				oneOf(baseCheck).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		assertFalse(check.check(target, creds, enforcer));
	}
	
	@Test
	public void testCheck2() {
		context.checking(new Expectations() {

			{
				oneOf(baseCheck).check(target, creds, enforcer);
				will(returnValue(false));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}

}
