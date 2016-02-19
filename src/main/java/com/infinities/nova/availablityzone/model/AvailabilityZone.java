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
package com.infinities.nova.availablityzone.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author pohsun
 *
 */
public class AvailabilityZone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final class ZoneState implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Boolean available;


		/**
		 * @return the available
		 */
		public Boolean getAvailable() {
			return available;
		}

		/**
		 * @param available
		 *            the available to set
		 */
		public void setAvailable(Boolean available) {
			this.available = available;
		}

	}


	private ZoneState zoneState;
	private String zoneName;
	private Map<String, Map<String, Service>> hosts;


	/**
	 * @return the zoneState
	 */
	public ZoneState getZoneState() {
		return zoneState;
	}

	/**
	 * @param zoneState
	 *            the zoneState to set
	 */
	public void setZoneState(ZoneState zoneState) {
		this.zoneState = zoneState;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @param zoneName
	 *            the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * @return the hosts
	 */
	public Map<String, Map<String, Service>> getHosts() {
		return hosts;
	}

	/**
	 * @param hosts
	 *            the hosts to set
	 */
	public void setHosts(Map<String, Map<String, Service>> hosts) {
		this.hosts = hosts;
	}

}
