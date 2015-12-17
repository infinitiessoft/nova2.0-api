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
package com.infinities.nova.db.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "NETWORK", uniqueConstraints = { @UniqueConstraint(columnNames = { "VLAN", "DELETED" }) })
public class Network extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String label;
	private Boolean injected = false;
	private String cidr;
	private String cidrV6;
	private Boolean multiHost = false;
	private String gatewayV6;
	private String netmaskV6;
	private String netmask;
	private String bridge;
	private String bridgeInterface;
	private String gateway;
	private String broadcast;
	private String dns1;
	private String dns2;
	private Integer vlan;
	private String vpnPublicAddress;
	private Integer vpnPublicPort;
	private String vpnPrivateAddress;
	private String dhcpStart;
	private Integer rxtxBase;
	private String projectId;
	private Integer priority;
	private String host;
	private String uuid;
	private Integer mtu;
	private String dhcpServer;
	private Boolean enableDhcp = true;
	private Boolean shareAddress = false;
	private Set<FixedIp> fixedIps = new HashSet<FixedIp>(0);


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LABEL", length = 255)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "INJECTED")
	public Boolean getInjected() {
		return injected;
	}

	public void setInjected(Boolean injected) {
		this.injected = injected;
	}

	@Column(name = "CIDR", length = 255)
	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	@Column(name = "CIDR_V6", length = 255)
	public String getCidrV6() {
		return cidrV6;
	}

	public void setCidrV6(String cidrV6) {
		this.cidrV6 = cidrV6;
	}

	@Column(name = "MULTI_HOST")
	public Boolean getMultiHost() {
		return multiHost;
	}

	public void setMultiHost(Boolean multiHost) {
		this.multiHost = multiHost;
	}

	@Column(name = "GATEWAY_V6", length = 255)
	public String getGatewayV6() {
		return gatewayV6;
	}

	public void setGatewayV6(String gatewayV6) {
		this.gatewayV6 = gatewayV6;
	}

	@Column(name = "NETMASK_V6", length = 255)
	public String getNetmaskV6() {
		return netmaskV6;
	}

	public void setNetmaskV6(String netmaskV6) {
		this.netmaskV6 = netmaskV6;
	}

	@Column(name = "NETMASK", length = 255)
	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	@Column(name = "BRIDGE", length = 255)
	public String getBridge() {
		return bridge;
	}

	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	@Column(name = "BRIDGE_INTERFACE", length = 255)
	public String getBridgeInterface() {
		return bridgeInterface;
	}

	public void setBridgeInterface(String bridgeInterface) {
		this.bridgeInterface = bridgeInterface;
	}

	@Column(name = "GATEWAY", length = 255)
	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	@Column(name = "BOARDCAST", length = 255)
	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	@Column(name = "DNS1", length = 255)
	public String getDns1() {
		return dns1;
	}

	public void setDns1(String dns1) {
		this.dns1 = dns1;
	}

	@Column(name = "DNS2", length = 255)
	public String getDns2() {
		return dns2;
	}

	public void setDns2(String dns2) {
		this.dns2 = dns2;
	}

	@Column(name = "VLAN")
	public Integer getVlan() {
		return vlan;
	}

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

	@Column(name = "VPN_PUBLIC_ADDRESS", length = 255)
	public String getVpnPublicAddress() {
		return vpnPublicAddress;
	}

	public void setVpnPublicAddress(String vpnPublicAddress) {
		this.vpnPublicAddress = vpnPublicAddress;
	}

	@Column(name = "VPN_PUBLIC_PORT")
	public Integer getVpnPublicPort() {
		return vpnPublicPort;
	}

	public void setVpnPublicPort(Integer vpnPublicPort) {
		this.vpnPublicPort = vpnPublicPort;
	}

	@Column(name = "VPN_PRIVATE_ADDRESS", length = 255)
	public String getVpnPrivateAddress() {
		return vpnPrivateAddress;
	}

	public void setVpnPrivateAddress(String vpnPrivateAddress) {
		this.vpnPrivateAddress = vpnPrivateAddress;
	}

	@Column(name = "DHCP_START", length = 255)
	public String getDhcpStart() {
		return dhcpStart;
	}

	public void setDhcpStart(String dhcpStart) {
		this.dhcpStart = dhcpStart;
	}

	@Column(name = "RXTX_BASE")
	public Integer getRxtxBase() {
		return rxtxBase;
	}

	public void setRxtxBase(Integer rxtxBase) {
		this.rxtxBase = rxtxBase;
	}

	@Column(name = "PROJECT_ID", length = 255)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "HOST", length = 255)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "UUID", length = 36)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "MTU")
	public Integer getMtu() {
		return mtu;
	}

	public void setMtu(Integer mtu) {
		this.mtu = mtu;
	}
	
	@Column(name = "DHCP_SERVER", length = 255)
	public String getDhcpServer() {
		return dhcpServer;
	}

	public void setDhcpServer(String dhcpServer) {
		this.dhcpServer = dhcpServer;
	}

	@Column(name = "ENABLE_DHCP")
	public Boolean getEnableDhcp() {
		return enableDhcp;
	}

	public void setEnableDhcp(Boolean enableDhcp) {
		this.enableDhcp = enableDhcp;
	}

	@Column(name = "SHARE_ADDRESS")
	public Boolean getShareAddress() {
		return shareAddress;
	}

	public void setShareAddress(Boolean shareAddress) {
		this.shareAddress = shareAddress;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "network", cascade = CascadeType.REMOVE)
	public Set<FixedIp> getFixedIps() {
		return fixedIps;
	}

	public void setFixedIps(Set<FixedIp> fixedIps) {
		this.fixedIps = fixedIps;
	}

}
