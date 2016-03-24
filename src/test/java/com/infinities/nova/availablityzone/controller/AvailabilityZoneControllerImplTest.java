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
package com.infinities.nova.availablityzone.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.nova.api.AbstractControllerTest;
import com.infinities.nova.availablityzone.api.AvailabilityZoneApi;
import com.infinities.nova.availablityzone.model.AvailabilityZone;
import com.infinities.nova.availablityzone.model.AvailabilityZoneTemplate;

/**
 * @author pohsun
 *
 */
public class AvailabilityZoneControllerImplTest extends AbstractControllerTest {

	private AvailabilityZoneController controller;
	private AvailabilityZoneApi api;
	private AvailabilityZone zone;
	private List<AvailabilityZone> zones;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		api = context.mock(AvailabilityZoneApi.class);
		controller = new AvailabilityZoneControllerImpl(api);
		zone = new AvailabilityZone();
		zones = new ArrayList<AvailabilityZone>();
		zones.add(zone);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.infinities.nova.availablityzone.controller.AvailabilityZoneControllerImpl#index(javax.ws.rs.container.ContainerRequestContext)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIndex() throws Exception {
		context.checking(new Expectations() {

			{
				exactly(1).of(api).getAvailabilityZones(openstackContext);
				will(returnValue(zones));
			}
		});
		AvailabilityZoneTemplate template = controller.index(requestContext);
		List<AvailabilityZone> rets = template.getAvailabilityZoneInfo();
		assertEquals(zones.size(), rets.size());
	}

	/**
	 * Test method for
	 * {@link com.infinities.nova.availablityzone.controller.AvailabilityZoneControllerImpl#detail(javax.ws.rs.container.ContainerRequestContext)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDetail() throws Exception {
		context.checking(new Expectations() {

			{
				exactly(1).of(api).getAvailabilityZones(openstackContext);
				will(returnValue(zones));
			}
		});
		AvailabilityZoneTemplate template = controller.detail(requestContext);
		List<AvailabilityZone> rets = template.getAvailabilityZoneInfo();
		assertEquals(zones.size(), rets.size());
	}

}
