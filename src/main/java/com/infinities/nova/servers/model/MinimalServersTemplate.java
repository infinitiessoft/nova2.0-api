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
package com.infinities.nova.servers.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.infinities.api.openstack.commons.model.Link;

public class MinimalServersTemplate implements Iterable<MinimalServer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "servers")
	private List<MinimalServer> list;

	@XmlElement(name = "servers_links")
	private List<Link> links;


	/**
	 * @return the list
	 */
	public List<MinimalServer> getList() {
		return list;
	}

	@Override
	public Iterator<MinimalServer> iterator() {
		return list.iterator();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setList(List<MinimalServer> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinimalServersTemplate [list=" + list + "]";
	}

}
