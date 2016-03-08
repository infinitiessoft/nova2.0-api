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
package com.infinities.nova.flavors.views;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.base.Strings;
import com.infinities.api.openstack.commons.model.Link;
import com.infinities.api.openstack.commons.views.AbstractViewBuilder;
import com.infinities.nova.flavors.model.Flavor;
import com.infinities.nova.flavors.model.FlavorTemplate;
import com.infinities.nova.flavors.model.FlavorsTemplate;
import com.infinities.nova.flavors.model.MinimalFlavor;
import com.infinities.nova.flavors.model.MinimalFlavorTemplate;
import com.infinities.nova.flavors.model.MinimalFlavorsTemplate;

public class ViewBuilder extends AbstractViewBuilder {

	private int osapiMaxLimit;


	/**
	 * @param config
	 */
	public ViewBuilder(String osapiComputeLinkPrefix, int osapiMaxLimit) {
		super(osapiComputeLinkPrefix);
		this.osapiMaxLimit = osapiMaxLimit;
	}


	private final static String COLLECTION_NAME = "flavors";


	public MinimalFlavorTemplate basic(ContainerRequestContext requestContext, Flavor flavor) throws URISyntaxException {
		MinimalFlavor f = new MinimalFlavor();
		f.setId(flavor.getId());
		f.setName(flavor.getName());
		f.setLinks(getLinks(requestContext, flavor.getId(), COLLECTION_NAME));
		return new MinimalFlavorTemplate(f);
	}

	public FlavorTemplate show(ContainerRequestContext requestContext, Flavor flavor) throws URISyntaxException {
		Flavor f = new Flavor();
		f.setId(flavor.getId());
		f.setName(flavor.getName());
		f.setRam(flavor.getRam());
		f.setDisk(flavor.getDisk());
		f.setVcpus(flavor.getVcpus());
		// String swap = flavor.getSwap() == null ? "" :
		// flavor.getSwap().toString();
		// f.setSwap(swap);
		f.setLinks(getLinks(requestContext, flavor.getId(), COLLECTION_NAME));
		return new FlavorTemplate(f);
	}

	public MinimalFlavorsTemplate index(ContainerRequestContext requestContext, List<Flavor> flavors)
			throws URISyntaxException {
		List<MinimalFlavor> flavorList = new ArrayList<MinimalFlavor>();
		for (Flavor flavor : flavors) {
			flavorList.add(basic(requestContext, flavor).getFlavor());
		}
		List<Link> flavorsLinks = getCollectionLinks(requestContext, flavors, COLLECTION_NAME);

		MinimalFlavorsTemplate ret = new MinimalFlavorsTemplate();
		ret.setList(flavorList);
		ret.setLinks(flavorsLinks);
		return ret;
	}

	private List<Link> getCollectionLinks(ContainerRequestContext requestContext, List<Flavor> flavors, String collectionName)
			throws URISyntaxException {
		List<Link> links = new ArrayList<Link>();
		String limitQP = requestContext.getUriInfo().getQueryParameters().getFirst("limit");
		int limitQ = osapiMaxLimit;
		int maxItems = limitQ;

		if (!Strings.isNullOrEmpty(limitQP)) {
			int limit = Integer.parseInt(limitQP);
			maxItems = Math.min(maxItems, limit);
		}

		if (maxItems == flavors.size()) {
			int item = flavors.size() - 1;
			if (item >= 0) {
				Flavor lastItem = flavors.get(item);
				String lastItemId = lastItem.getId();
				Link link = new Link();
				link.setRel("next");
				link.setHref(getNextLink(requestContext, lastItemId, collectionName));
				links.add(link);
			}
		}

		if (links.isEmpty()) {
			return null;
		}

		return links;
	}

	public FlavorsTemplate detail(ContainerRequestContext requestContext, List<Flavor> flavors) throws URISyntaxException {
		List<Flavor> flavorList = new ArrayList<Flavor>();
		for (Flavor flavor : flavors) {
			flavorList.add(show(requestContext, flavor).getFlavor());
		}

		List<Link> flavorsLinks = getCollectionLinks(requestContext, flavors, COLLECTION_NAME);
		FlavorsTemplate ret = new FlavorsTemplate();
		ret.setList(flavorList);
		ret.setLinks(flavorsLinks);
		return ret;
	}

}
