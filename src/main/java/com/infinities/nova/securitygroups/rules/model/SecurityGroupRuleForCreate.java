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
package com.infinities.nova.securitygroups.rules.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "security_group_rule")
public class SecurityGroupRuleForCreate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "parent_group_id")
	private String parentGroupId;

	@XmlElement(name = "ip_protocol")
	private String ipProtocol;

	@XmlElement(name = "from_port")
	private Integer fromPort;

	@XmlElement(name = "to_port")
	private Integer toPort;

	@XmlElement
	private String cidr;

	@XmlElement(name = "group_id")
	private String groupId;


	public SecurityGroupRuleForCreate() {

	}

	public SecurityGroupRuleForCreate(String parentSecurityGroupId, String ipProtocol, Integer fromPort, Integer toPort,
			String cidr) {
		this.parentGroupId = parentSecurityGroupId;
		this.ipProtocol = ipProtocol;
		this.fromPort = fromPort;
		this.toPort = toPort;
		this.cidr = cidr;
	}

	public SecurityGroupRuleForCreate(String parentGroupId, String sourceGroupId, String ipProtocol, Integer fromPort,
			Integer toPort) {
		this.parentGroupId = parentGroupId;
		this.ipProtocol = ipProtocol;
		this.fromPort = fromPort;
		this.toPort = toPort;
		this.groupId = sourceGroupId;
	}

	/**
	 * @return the parentGroupId
	 */
	public String getParentGroupId() {
		return parentGroupId;
	}

	/**
	 * @param parentGroupId
	 *            the parentGroupId to set
	 */
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	/**
	 * @return the fromPort
	 */
	public Integer getFromPort() {
		return fromPort;
	}

	/**
	 * @param fromPort
	 *            the fromPort to set
	 */
	public void setFromPort(Integer fromPort) {
		this.fromPort = fromPort;
	}

	/**
	 * @return the toPort
	 */
	public Integer getToPort() {
		return toPort;
	}

	/**
	 * @param toPort
	 *            the toPort to set
	 */
	public void setToPort(Integer toPort) {
		this.toPort = toPort;
	}

	/**
	 * @return the ipProtocol
	 */
	public String getIpProtocol() {
		return ipProtocol;
	}

	/**
	 * @param ipProtocol
	 *            the ipProtocol to set
	 */
	public void setIpProtocol(String ipProtocol) {
		this.ipProtocol = ipProtocol;
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SecurityGroupRuleForCreate [parentGroupId=" + parentGroupId + ", fromPort=" + fromPort + ", toPort="
				+ toPort + ", ipProtocol=" + ipProtocol + ", cidr=" + cidr + ", groupId=" + groupId + "]";
	}

}
