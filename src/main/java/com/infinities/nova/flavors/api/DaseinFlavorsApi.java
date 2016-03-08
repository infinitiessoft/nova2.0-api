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
package com.infinities.nova.flavors.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.compute.VirtualMachineProduct;

import com.google.common.base.Preconditions;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.flavors.controller.FlavorsFilter;
import com.infinities.nova.flavors.model.Flavor;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedVirtualMachineSupport;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinFlavorsApi implements FlavorsApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinFlavorsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	// context=null, inactive=false,
	// filters=null,sortKey=flavorid,sortDir=asc,limit=null,marker=null
	@Override
	public List<Flavor> getAllFlavorsSortedList(OpenstackRequestContext context, FlavorsFilter filter, String sortKey,
			String sortDir, Integer limit, String marker) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<VirtualMachineProduct>> result = getSupport(context.getProjectId()).listAllProducts();
		Iterable<VirtualMachineProduct> iterable = result.get();
		Iterator<VirtualMachineProduct> iterator = iterable.iterator();
		List<Flavor> instTypes = new ArrayList<Flavor>();
		while (iterator.hasNext()) {
			VirtualMachineProduct product = iterator.next();
			Flavor type = toFlavor(product);
			instTypes.add(type);
		}

		return instTypes;
	}

	private Flavor toFlavor(VirtualMachineProduct product) {
		Flavor type = new Flavor();
		type.setId(product.getProviderProductId());
		type.setName(product.getName());
		type.setVcpus(product.getCpuCount());
		if (product.getRamSize() != null) {
			type.setRam(product.getRamSize().intValue());
		}
		if (product.getRootVolumeSize() != null) {
			type.setDisk(product.getRootVolumeSize().intValue());
		}
		return type;
	}

	// context=null,readDeleted = yes
	@Override
	public Flavor getFlavorByFlavorId(String flavorid, OpenstackRequestContext context, String readDeleted) throws Exception {
		if (context == null) {
			context = Context.getAdminContext(readDeleted);
		}

		AsyncResult<VirtualMachineProduct> result = getSupport(context.getProjectId()).getProduct(flavorid);
		VirtualMachineProduct product = result.get();
		if (product == null) {
			throw new IllegalArgumentException("invalid flavor:" + flavorid);
		}
		return toFlavor(product);
	}

	private CachedVirtualMachineSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasVirtualMachineSupport()) {
				return provider.getComputeServices().getVirtualMachineSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}
}
