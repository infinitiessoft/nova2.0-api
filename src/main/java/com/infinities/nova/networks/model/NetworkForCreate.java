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
package com.infinities.nova.networks.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class NetworkForCreate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
	private String cidr;
	private Integer mtu;
	@XmlElement(name = "dhcp_server")
	private String dhcpServer;
	@XmlElement(name = "enable_dhcp")
	private Boolean enableDhcp;
	@XmlElement(name = "share_address")
	private Boolean shareAddress;
	@XmlElement(name = "allowed_start")
	private String allowedStart;
	@XmlElement(name = "allowed_end")
	private String allowedEnd;


	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the cidr
	 */
	public String getCidr() {
		return cidr;
	}

	/**
	 * @param cidr
	 *            the cidr to set
	 */
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	/**
	 * @return the mtu
	 */
	public Integer getMtu() {
		return mtu;
	}

	/**
	 * @param mtu
	 *            the mtu to set
	 */
	public void setMtu(Integer mtu) {
		this.mtu = mtu;
	}

	/**
	 * @return the dhcpServer
	 */
	public String getDhcpServer() {
		return dhcpServer;
	}

	/**
	 * @param dhcpServer
	 *            the dhcpServer to set
	 */
	public void setDhcpServer(String dhcpServer) {
		this.dhcpServer = dhcpServer;
	}

	/**
	 * @return the enableDhcp
	 */
	public Boolean getEnableDhcp() {
		return enableDhcp;
	}

	/**
	 * @param enableDhcp
	 *            the enableDhcp to set
	 */
	public void setEnableDhcp(Boolean enableDhcp) {
		this.enableDhcp = enableDhcp;
	}

	/**
	 * @return the shareAddress
	 */
	public Boolean getShareAddress() {
		return shareAddress;
	}

	/**
	 * @param shareAddress
	 *            the shareAddress to set
	 */
	public void setShareAddress(Boolean shareAddress) {
		this.shareAddress = shareAddress;
	}

	/**
	 * @return the allowedStart
	 */
	public String getAllowedStart() {
		return allowedStart;
	}

	/**
	 * @param allowedStart
	 *            the allowedStart to set
	 */
	public void setAllowedStart(String allowedStart) {
		this.allowedStart = allowedStart;
	}

	/**
	 * @return the allowedEnd
	 */
	public String getAllowedEnd() {
		return allowedEnd;
	}

	/**
	 * @param allowedEnd
	 *            the allowedEnd to set
	 */
	public void setAllowedEnd(String allowedEnd) {
		this.allowedEnd = allowedEnd;
	}

}
