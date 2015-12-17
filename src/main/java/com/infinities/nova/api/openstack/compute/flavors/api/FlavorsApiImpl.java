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

import java.util.List;

import com.infinities.nova.api.Context;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsFilter;
import com.infinities.nova.db.model.InstanceType;
import com.infinities.nova.model.home.InstanceTypeHome;
import com.infinities.nova.model.home.impl.InstanceTypeHomeImpl;

public class FlavorsApiImpl implements FlavorsApi {

	private final InstanceTypeHome db;


	public FlavorsApiImpl() {
		db = new InstanceTypeHomeImpl();
	}

	// context=null, inactive=false,
	// filters=null,sortKey=flavorid,sortDir=asc,limit=null,marker=null
	@Override
	public List<InstanceType> getAllFlavorsSortedList(NovaRequestContext context, FlavorsFilter filter, String sortKey,
			String sortDir, Integer limit, String marker) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		List<InstanceType> instTypes = db.flavorGetAll(context, false, filter, sortKey, sortDir, limit, marker);
		return instTypes;
	}

	// context=null,readDeleted = yes
	@Override
	public InstanceType getFlavorByFlavorId(String flavorid, NovaRequestContext context, String readDeleted)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext(readDeleted);
		}
		return db.flavorGetByFlavorId(context, flavorid, readDeleted);
	}

	// @Override
	// public InstanceType getDefaultFlavor() throws Exception {
	// String name = Config.Instance.getOpt("default_flavor").asText();
	// return getFlavorByName(name, null);
	// }
	//
	// private InstanceType getFlavorByName(String name, NovaRequestContext
	// context) throws Exception {
	// if (Strings.isNullOrEmpty(name)) {
	// return getDefaultFlavor();
	// }
	// if (context == null) {
	// context = Context.getAdminContext("no");
	// }
	//
	// return db.flavorGetByName(context, name);
	// }
}
