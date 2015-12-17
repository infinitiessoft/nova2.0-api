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
package com.infinities.nova.api.openstack.compute.limits;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.infinities.skyport.util.JsonUtil;

public class LimitTest {

	private Limit limit;
	private String verb = "post";
	private URI uri;
	private String regex = ".*/skyport";
	private int value = 5;
	private TimeUnit unit = TimeUnit.MINUTES;


	@Before
	public void setUp() throws Exception {
		uri = new URI("http://localhost/skyport");
		limit = new Limit(verb, uri, regex, value, unit);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCall() {
		// 1
		String ret = limit.call("post", uri);
		assertNull(ret);
		// 2
		ret = limit.call("post", uri);
		assertNull(ret);
		// 3
		ret = limit.call("post", uri);
		assertNull(ret);
		// 4
		ret = limit.call("post", uri);
		assertNull(ret);
		// 5
		ret = limit.call("post", uri);
		assertNull(ret);
		// 6
		ret = limit.call("post", uri);
		assertNotNull(ret);
	}

	@Test
	public void testDisplay() throws IOException {
		LimitWrapper wrapper = limit.display();
		System.out.println(JsonUtil.toString(wrapper));
	}

}
