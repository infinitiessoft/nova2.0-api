package com.infinities.nova.openstack.common.policy.reducer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import jersey.repackaged.com.google.common.collect.Maps;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.BaseReducer;
import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;
import com.infinities.nova.openstack.common.policy.check.AndCheck;

public class ExtendAndExprReducerTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private ExtendAndExprReducer reducer;
	private BaseReducer base;
	private Target target;
	private Credentials creds;
	private Enforcer enforcer;
	private BaseCheck check2, check3, check4, check5;
	private AndCheck andCheck;


	@Before
	public void setUp() throws Exception {
		base = context.mock(BaseReducer.class);
		reducer = new ExtendAndExprReducer(base);
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		enforcer = context.mock(Enforcer.class);
		// check1 = context.mock(BaseCheck.class, "check1");
		check2 = context.mock(BaseCheck.class, "check2");
		check3 = context.mock(BaseCheck.class, "check3");
		check4 = context.mock(BaseCheck.class, "check4");
		check5 = context.mock(BaseCheck.class, "check5");
		List<BaseCheck> checks = new ArrayList<BaseCheck>();
		checks.add(check4);
		checks.add(check5);
		andCheck = new AndCheck(checks);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetEntry() {
		List<Entry<String, BaseCheck>> entrys = new ArrayList<Entry<String, BaseCheck>>();
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("check", (BaseCheck) andCheck);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("check2", check2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("check3", check3);
		entrys.add(entry1);
		entrys.add(entry2);
		entrys.add(entry3);
		Entry<String, BaseCheck> ret = reducer.getEntry(entrys);
		assertEquals("and_expr", ret.getKey());
		assertTrue(ret.getValue() instanceof AndCheck);
		AndCheck retCheck = (AndCheck) ret.getValue();

		context.checking(new Expectations() {

			{
				exactly(1).of(check3).check(target, creds, enforcer);
				will(returnValue(true));

				oneOf(check4).check(target, creds, enforcer);
				will(returnValue(true));

				exactly(1).of(check5).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		assertTrue(retCheck.check(target, creds, enforcer));
	}

	@Test
	public void testReduce() {
		final List<Entry<String, BaseCheck>> entrys = new ArrayList<Entry<String, BaseCheck>>();
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("check", (BaseCheck) andCheck);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("check2", check2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("check3", check3);
		entrys.add(entry1);
		entrys.add(entry2);
		entrys.add(entry3);
		final BaseCheck check6 = context.mock(BaseCheck.class, "check6");
		final Entry<String, BaseCheck> entry = Maps.immutableEntry("check6", check6);
		context.checking(new Expectations() {

			{
				oneOf(base).reduce(entrys);
				will(returnValue(entry));

			}
		});
		Entry<String, BaseCheck> ret = reducer.reduce(entrys);
		assertEquals(entry, ret);
	}

	@Test
	public void testReduce2() {
		final List<Entry<String, BaseCheck>> entrys = new ArrayList<Entry<String, BaseCheck>>();
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("and_expr", (BaseCheck) andCheck);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("and", check2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("check", check3);
		entrys.add(entry1);
		entrys.add(entry2);
		entrys.add(entry3);
		context.checking(new Expectations() {

			{

				exactly(1).of(check3).check(target, creds, enforcer);
				will(returnValue(true));

				oneOf(check4).check(target, creds, enforcer);
				will(returnValue(true));

				exactly(1).of(check5).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		Entry<String, BaseCheck> ret = reducer.reduce(entrys);
		assertEquals("and_expr", ret.getKey());
		assertTrue(ret.getValue() instanceof AndCheck);
		assertTrue(ret.getValue().check(target, creds, enforcer));
	}

	@Test
	public void testReduce3() {
		final List<Entry<String, BaseCheck>> entrys = new ArrayList<Entry<String, BaseCheck>>();
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("and_expr", (BaseCheck) andCheck);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("and", check2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("check", check3);
		entrys.add(entry1);
		entrys.add(entry2);
		entrys.add(entry3);
		context.checking(new Expectations() {

			{

				exactly(1).of(check3).check(target, creds, enforcer);
				will(returnValue(false));

				oneOf(check4).check(target, creds, enforcer);
				will(returnValue(true));

				exactly(1).of(check5).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		Entry<String, BaseCheck> ret = reducer.reduce(entrys);
		assertEquals("and_expr", ret.getKey());
		assertTrue(ret.getValue() instanceof AndCheck);
		assertFalse(ret.getValue().check(target, creds, enforcer));
	}

	@Test
	public void testReduce4() {
		final List<Entry<String, BaseCheck>> entrys = new ArrayList<Entry<String, BaseCheck>>();
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("and_expr", (BaseCheck) andCheck);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("and", check2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("check", check3);
		entrys.add(entry1);
		entrys.add(entry2);
		entrys.add(entry3);
		context.checking(new Expectations() {

			{

				exactly(1).of(check3).check(target, creds, enforcer);
				will(returnValue(true));

				oneOf(check4).check(target, creds, enforcer);
				will(returnValue(true));

				exactly(1).of(check5).check(target, creds, enforcer);
				will(returnValue(false));
			}
		});
		Entry<String, BaseCheck> ret = reducer.reduce(entrys);
		assertEquals("and_expr", ret.getKey());
		assertTrue(ret.getValue() instanceof AndCheck);
		assertFalse(ret.getValue().check(target, creds, enforcer));
	}

	@Test
	public void testReduce5() {
		final List<Entry<String, BaseCheck>> entrys = new ArrayList<Entry<String, BaseCheck>>();
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("and_expr", (BaseCheck) andCheck);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("and", check2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("check", check3);
		entrys.add(entry1);
		entrys.add(entry2);
		entrys.add(entry3);
		context.checking(new Expectations() {

			{

				exactly(1).of(check3).check(target, creds, enforcer);
				will(returnValue(true));

				oneOf(check4).check(target, creds, enforcer);
				will(returnValue(false));

				exactly(1).of(check5).check(target, creds, enforcer);
				will(returnValue(true));
			}
		});
		Entry<String, BaseCheck> ret = reducer.reduce(entrys);
		assertEquals("and_expr", ret.getKey());
		assertTrue(ret.getValue() instanceof AndCheck);
		assertFalse(ret.getValue().check(target, creds, enforcer));
	}

}
