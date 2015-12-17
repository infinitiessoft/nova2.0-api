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
package com.infinities.nova.api.openstack.compute.flavors.api;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.infinities.nova.api.AbstractDbUnitTest;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsFilter;
import com.infinities.nova.db.model.InstanceType;

@RunWith(JUnitParamsRunner.class)
public class FlavorsApiImplTest extends AbstractDbUnitTest {

	private FlavorsApi api;


	public FlavorsApiImplTest() {
	}

	@Before
	public void setUp() throws Exception {
		api = new FlavorsApiImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Parameters(method = "testGetAllFlavorsSortedListParams")
	public void testGetAllFlavorsSortedList(String sortKey, String sortDir, Integer limit, String marker,
			Integer minMemoryMb, Integer minRootGb, Boolean disabled, Boolean isPublic, int size) throws Exception {
		FlavorsFilter filter = new FlavorsFilter();
		filter.setMinMemoryMb(minMemoryMb);
		filter.setMinRootGb(minRootGb);
		filter.setDisabled(disabled);
		filter.setDisabled(disabled);
		List<InstanceType> rets = api.getAllFlavorsSortedList(null, filter, sortKey, sortDir, limit, marker);
		assertEquals(size, rets.size());
	}

	public Object[] testGetAllFlavorsSortedListParams() {
		return $($("flavorid", "asc", 1, null, null, null, null, null, 1),
				$("flavorid", "asc", 5, null, null, null, null, null, 5),
				$("flavorid", "asc", 5, "2", null, null, null, null, 3),
				$("flavorid", "asc", 5, "3", null, null, null, null, 2),
				$("flavorid", "asc", 1, "3", null, null, null, null, 1),
				$("flavorid", "asc", 5, null, 8192, null, null, null, 2),
				$("flavorid", "asc", 5, null, null, 80, null, null, 2),
				$("flavorid", "asc", 5, null, null, null, true, null, 0),
				$("flavorid", "asc", 5, null, null, null, null, false, 5),
				$("flavorid", "asc", 5, "4", 8192, 80, false, true, 1));
	}

	@Test
	@Parameters(method = "testGetFlavorByFlavorIdParams")
	public void testGetFlavorByFlavorId(String flavorid, String readDeleted, boolean expectExcetption) throws Exception {
		if (!expectExcetption) {
			InstanceType ret = api.getFlavorByFlavorId(flavorid, null, readDeleted);
			assertEquals(flavorid, ret.getFlavorid());
		} else {
			try {
				api.getFlavorByFlavorId(flavorid, null, readDeleted);
				fail("didn't throw an exception!");
			} catch (Exception e) {
				// pass
			}
		}
	}

	public Object[] testGetFlavorByFlavorIdParams() {
		return $($("1", "no", false), $("2", "no", false));
	}

}
