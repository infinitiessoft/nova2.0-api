package com.infinities.nova.openstack.common.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import jersey.repackaged.com.google.common.collect.Maps;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.openstack.common.policy.check.FalseCheck;
import com.infinities.nova.openstack.common.policy.check.StringCheck;
import com.infinities.nova.openstack.common.policy.check.TrueCheck;

public class ParseStateTest {

	private ParseState state;
	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private Target target;
	private Credentials creds;


	@Before
	public void setUp() throws Exception {
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		enforcer = context.mock(Enforcer.class);
		state = new ParseState();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShift() {
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("(", (BaseCheck) new StringCheck("("));
		state.shift(entry1);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("check", (BaseCheck) new TrueCheck());
		state.shift(entry2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("and", (BaseCheck) new StringCheck("and"));
		state.shift(entry3);
		Entry<String, BaseCheck> entry4 = Maps.immutableEntry("check", (BaseCheck) new FalseCheck());
		state.shift(entry4);
		Entry<String, BaseCheck> entry5 = Maps.immutableEntry(")", (BaseCheck) new StringCheck(")"));
		state.shift(entry5);
		Entry<String, BaseCheck> ret = state.getResult();
		assertFalse(ret.getValue().check(target, creds, enforcer));
	}

	@Test
	public void testShift2() {
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("(", (BaseCheck) new StringCheck("("));
		state.shift(entry1);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("check", (BaseCheck) new TrueCheck());
		state.shift(entry2);
		Entry<String, BaseCheck> entry3 = Maps.immutableEntry("and", (BaseCheck) new StringCheck("and"));
		state.shift(entry3);
		Entry<String, BaseCheck> entry4 = Maps.immutableEntry("check", (BaseCheck) new TrueCheck());
		state.shift(entry4);
		Entry<String, BaseCheck> entry5 = Maps.immutableEntry(")", (BaseCheck) new StringCheck(")"));
		state.shift(entry5);
		Entry<String, BaseCheck> ret = state.getResult();
		assertTrue(ret.getValue().check(target, creds, enforcer));
	}

	@Test(expected = IllegalStateException.class)
	public void testGetResult() {
		Entry<String, BaseCheck> entry1 = Maps.immutableEntry("(", (BaseCheck) new StringCheck("("));
		state.shift(entry1);
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("check", (BaseCheck) new TrueCheck());
		state.shift(entry2);
		state.getResult();
	}
	
	@Test
	public void testGetResult2() {
		Entry<String, BaseCheck> entry2 = Maps.immutableEntry("check", (BaseCheck) new TrueCheck());
		state.shift(entry2);
		state.getResult();
	}

}
