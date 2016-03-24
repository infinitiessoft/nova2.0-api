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
package com.infinities.nova.api;

import javax.ws.rs.container.ContainerRequestContext;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;

import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;

/**
 * @author pohsun
 *
 */
public abstract class AbstractControllerTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	protected final static String NOVA_CONTEXT = "nova.context";
	protected ContainerRequestContext requestContext;
	protected OpenstackRequestContext openstackContext;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void init() throws Exception {
		requestContext = context.mock(ContainerRequestContext.class);
		openstackContext = Context.getAdminContext("no");
		context.checking(new Expectations() {

			{
				exactly(1).of(requestContext).getProperty(NOVA_CONTEXT);
				will(returnValue(openstackContext));
			}
		});
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void end() throws Exception {
		context.assertIsSatisfied();
	}

}
