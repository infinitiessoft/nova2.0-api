package com.infinities.nova.openstack.common.policy.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

public class OrCheckTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private Target target;
	private Credentials creds;
	private OrCheck check;
	private BaseCheck baseCheck1, baseCheck2;


	@Before
	public void setUp() throws Exception {
		enforcer = context.mock(Enforcer.class);
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		baseCheck1 = context.mock(BaseCheck.class, "check1");
		baseCheck2 = context.mock(BaseCheck.class, "check2");
		List<BaseCheck> checks = new ArrayList<BaseCheck>();
		checks.add(baseCheck1);
		checks.add(baseCheck2);
		check = new OrCheck(checks);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheck() {
		context.checking(new Expectations() {

			{
				oneOf(baseCheck1).check(target, creds, enforcer);
				will(returnValue(true));
				oneOf(baseCheck2).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}

	@Test
	public void testCheck2() {
		context.checking(new Expectations() {

			{
				oneOf(baseCheck1).check(target, creds, enforcer);
				will(returnValue(false));
				oneOf(baseCheck2).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}

	@Test
	public void testCheck3() {
		context.checking(new Expectations() {

			{
				oneOf(baseCheck1).check(target, creds, enforcer);
				will(returnValue(true));
				oneOf(baseCheck2).check(target, creds, enforcer);
				will(returnValue(false));
			}
		});
		assertTrue(check.check(target, creds, enforcer));
	}

	@Test
	public void testCheck4() {
		context.checking(new Expectations() {

			{
				oneOf(baseCheck1).check(target, creds, enforcer);
				will(returnValue(false));
				oneOf(baseCheck2).check(target, creds, enforcer);
				will(returnValue(false));
			}
		});
		assertFalse(check.check(target, creds, enforcer));
	}

}
