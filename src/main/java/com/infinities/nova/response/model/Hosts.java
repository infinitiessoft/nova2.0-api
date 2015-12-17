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
package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class Hosts implements Iterable<Hosts.Host>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final class Host {

		private String zone;

		@XmlElement(name = "host_name")
		private String hostName;

		private String service;

		/**
		 * @return the hostName
		 */
		public String getHostName() {
			return hostName;
		}

		/**
		 * @return the service
		 */
		public String getService() {
			return service;
		}

		public String getZone() {
			return zone;
		}

		public void setZone(String zone) {
			this.zone = zone;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Host [hostName=" + hostName + ", service=" + service + "]";
		}

	}

	@XmlElement(name = "hosts")
	private List<Host> list;

	/**
	 * @return the list
	 */
	public List<Host> getList() {
		return list;
	}

	@Override
	public Iterator<Hosts.Host> iterator() {
		return list.iterator();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hosts [list=" + list + "]";
	}



}
