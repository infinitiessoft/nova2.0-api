/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.nova.policy.check;

import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.policy.Credentials;
import com.infinities.nova.policy.Enforcer;
import com.infinities.nova.policy.Target;
import com.infinities.nova.policy.check.TrueCheck;

public class TrueCheckTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Enforcer enforcer;
	private Target target;
	private Credentials creds;
	private TrueCheck check;


	@Before
	public void setUp() throws Exception {
		enforcer = context.mock(Enforcer.class);
		target = context.mock(Target.class);
		creds = context.mock(Credentials.class);
		check = new TrueCheck();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheck() {
		assertTrue(check.check(target, creds, enforcer));
	}

}