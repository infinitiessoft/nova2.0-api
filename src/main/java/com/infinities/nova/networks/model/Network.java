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
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "network")
public class Network implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	@XmlElement(name = "vpn_public_address")
	private String vpnPublicAddress;

	@XmlElement(name = "vpn_private_address")
	private String vpnPrivateAddress;

	@XmlElement(name = "vpn_public_port")
	private String vpnPublicPort;

	@XmlElement(name = "dhcp_start")
	private String dhcpStart;

	private String bridge;

	@XmlElement(name = "bridge_interface")
	private String bridgeInterface;

	@XmlElement(name = "updated_at")
	private String updatedAt;

	private Boolean deleted;

	private String vlan;

	private String broadcast;

	private String netmask;

	private Boolean injected;

	private String host;

	@XmlElement(name = "multi_host")
	private Boolean multiHost;

	@XmlElement(name = "created_at")
	private String createdAt;

	@XmlElement(name = "deleted_at")
	private String deletedAt;

	private String cidr;

	@XmlElement(name = "cidr_v6")
	private String cidrV6;

	private String gateway;

	@XmlElement(name = "gateway_v6")
	private String gatewayV6;

	@XmlElement(name = "netmask_v6")
	private String netmaskV6;

	@XmlElement(name = "project_id")
	private String projectId;

	@XmlElement(name = "rxtx_base")
	private String rxtxBase;

	private String dns1;

	private String dns2;

	private String label;

	private String priority;


	/**
	 * @return the vpnPrivateAddress
	 */
	public String getVpnPrivateAddress() {
		return vpnPrivateAddress;
	}

	/**
	 * @param vpnPrivateAddress
	 *            the vpnPrivateAddress to set
	 */
	public void setVpnPrivateAddress(String vpnPrivateAddress) {
		this.vpnPrivateAddress = vpnPrivateAddress;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param vpnPublicAddress
	 *            the vpnPublicAddress to set
	 */
	public void setVpnPublicAddress(String vpnPublicAddress) {
		this.vpnPublicAddress = vpnPublicAddress;
	}

	/**
	 * @param vpnPublicPort
	 *            the vpnPublicPort to set
	 */
	public void setVpnPublicPort(String vpnPublicPort) {
		this.vpnPublicPort = vpnPublicPort;
	}

	/**
	 * @param dhcpStart
	 *            the dhcpStart to set
	 */
	public void setDhcpStart(String dhcpStart) {
		this.dhcpStart = dhcpStart;
	}

	/**
	 * @param bridge
	 *            the bridge to set
	 */
	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	/**
	 * @param bridgeInterface
	 *            the bridgeInterface to set
	 */
	public void setBridgeInterface(String bridgeInterface) {
		this.bridgeInterface = bridgeInterface;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @param vlan
	 *            the vlan to set
	 */
	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	/**
	 * @param broadcast
	 *            the broadcast to set
	 */
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	/**
	 * @param netmask
	 *            the netmask to set
	 */
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	/**
	 * @param injected
	 *            the injected to set
	 */
	public void setInjected(Boolean injected) {
		this.injected = injected;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @param multiHost
	 *            the multiHost to set
	 */
	public void setMultiHost(Boolean multiHost) {
		this.multiHost = multiHost;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @param deletedAt
	 *            the deletedAt to set
	 */
	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	/**
	 * @param cidr
	 *            the cidr to set
	 */
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	/**
	 * @param cidrV6
	 *            the cidrV6 to set
	 */
	public void setCidrV6(String cidrV6) {
		this.cidrV6 = cidrV6;
	}

	/**
	 * @param gateway
	 *            the gateway to set
	 */
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	/**
	 * @param gatewayV6
	 *            the gatewayV6 to set
	 */
	public void setGatewayV6(String gatewayV6) {
		this.gatewayV6 = gatewayV6;
	}

	/**
	 * @param netmaskV6
	 *            the netmaskV6 to set
	 */
	public void setNetmaskV6(String netmaskV6) {
		this.netmaskV6 = netmaskV6;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * @param rxtxBase
	 *            the rxtxBase to set
	 */
	public void setRxtxBase(String rxtxBase) {
		this.rxtxBase = rxtxBase;
	}

	/**
	 * @param dns1
	 *            the dns1 to set
	 */
	public void setDns1(String dns1) {
		this.dns1 = dns1;
	}

	/**
	 * @param dns2
	 *            the dns2 to set
	 */
	public void setDns2(String dns2) {
		this.dns2 = dns2;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the vpnPublicAddress
	 */
	public String getVpnPublicAddress() {
		return vpnPublicAddress;
	}

	/**
	 * @return the vpnPublicPort
	 */
	public String getVpnPublicPort() {
		return vpnPublicPort;
	}

	/**
	 * @return the dhcpStart
	 */
	public String getDhcpStart() {
		return dhcpStart;
	}

	/**
	 * @return the bridge
	 */
	public String getBridge() {
		return bridge;
	}

	/**
	 * @return the bridgeInterface
	 */
	public String getBridgeInterface() {
		return bridgeInterface;
	}

	/**
	 * @return the updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @return the vlan
	 */
	public String getVlan() {
		return vlan;
	}

	/**
	 * @return the broadcast
	 */
	public String getBroadcast() {
		return broadcast;
	}

	/**
	 * @return the netmask
	 */
	public String getNetmask() {
		return netmask;
	}

	/**
	 * @return the injected
	 */
	public Boolean getInjected() {
		return injected;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the multiHost
	 */
	public Boolean getMultiHost() {
		return multiHost;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the deletedAt
	 */
	public String getDeletedAt() {
		return deletedAt;
	}

	/**
	 * @return the cidr
	 */
	public String getCidr() {
		return cidr;
	}

	/**
	 * @return the cidrV6
	 */
	public String getCidrV6() {
		return cidrV6;
	}

	/**
	 * @return the gateway
	 */
	public String getGateway() {
		return gateway;
	}

	/**
	 * @return the gatewayV6
	 */
	public String getGatewayV6() {
		return gatewayV6;
	}

	/**
	 * @return the netmaskV6
	 */
	public String getNetmaskV6() {
		return netmaskV6;
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @return the rxtxBase
	 */
	public String getRxtxBase() {
		return rxtxBase;
	}

	/**
	 * @return the dns1
	 */
	public String getDns1() {
		return dns1;
	}

	/**
	 * @return the dns2
	 */
	public String getDns2() {
		return dns2;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Network [id=" + id + ", vpnPublicAddress=" + vpnPublicAddress + ", vpnPublicPort=" + vpnPublicPort
				+ ", dhcpStart=" + dhcpStart + ", bridge=" + bridge + ", bridgeInterface=" + bridgeInterface
				+ ", updatedAt=" + updatedAt + ", deleted=" + deleted + ", vlan=" + vlan + ", broadcast=" + broadcast
				+ ", netmask=" + netmask + ", injected=" + injected + ", host=" + host + ", multiHost=" + multiHost
				+ ", createdAt=" + createdAt + ", deletedAt=" + deletedAt + ", cidr=" + cidr + ", cidrV6=" + cidrV6
				+ ", gateway=" + gateway + ", gatewayV6=" + gatewayV6 + ", netmaskV6=" + netmaskV6 + ", projectId="
				+ projectId + ", rxtxBase=" + rxtxBase + ", dns1=" + dns1 + ", dns2=" + dns2 + ", label=" + label
				+ ", priority=" + priority + "]";
	}

}
