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
package com.infinities.nova.servers.interfaces.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author pohsun
 *
 */

@XmlRootElement(name = "interfaceAttachment")
public class InterfaceAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static class FixedIp implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@XmlElement(name = "subnet_id")
		private String subnetId;
		@XmlElement(name = "ip_address")
		private String ipAddress;


		/**
		 * @return the subnetId
		 */
		public String getSubnetId() {
			return subnetId;
		}

		/**
		 * @param subnetId
		 *            the subnetId to set
		 */
		public void setSubnetId(String subnetId) {
			this.subnetId = subnetId;
		}

		/**
		 * @return the ipAddress
		 */
		public String getIpAddress() {
			return ipAddress;
		}

		/**
		 * @param ipAddress
		 *            the ipAddress to set
		 */
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

	}


	@XmlElement(name = "port_state")
	private String portState;
	@XmlElement(name = "fixed_ips")
	private List<FixedIp> fixedIps;
	@XmlElement(name = "port_id")
	private String portId;
	@XmlElement(name = "net_id")
	private String netId;
	@XmlElement(name = "mac_addr")
	private String macAddr;


	/**
	 * @return the portState
	 */
	public String getPortState() {
		return portState;
	}

	/**
	 * @param portState
	 *            the portState to set
	 */
	public void setPortState(String portState) {
		this.portState = portState;
	}

	/**
	 * @return the fixedIps
	 */
	public List<FixedIp> getFixedIps() {
		return fixedIps;
	}

	/**
	 * @param fixedIps
	 *            the fixedIps to set
	 */
	public void setFixedIps(List<FixedIp> fixedIps) {
		this.fixedIps = fixedIps;
	}

	/**
	 * @return the portId
	 */
	public String getPortId() {
		return portId;
	}

	/**
	 * @param portId
	 *            the portId to set
	 */
	public void setPortId(String portId) {
		this.portId = portId;
	}

	/**
	 * @return the netId
	 */
	public String getNetId() {
		return netId;
	}

	/**
	 * @param netId
	 *            the netId to set
	 */
	public void setNetId(String netId) {
		this.netId = netId;
	}

	/**
	 * @return the macAddr
	 */
	public String getMacAddr() {
		return macAddr;
	}

	/**
	 * @param macAddr
	 *            the macAddr to set
	 */
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

}
