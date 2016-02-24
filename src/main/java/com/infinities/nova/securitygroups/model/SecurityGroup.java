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
package com.infinities.nova.securitygroups.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "security_group")
public class SecurityGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@XmlRootElement(name = "security_group_rule")
	public static final class Rule implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		public static final class Group implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private String name;

			@XmlElement(name = "tenant_id")
			private String tenantId;


			public String getName() {
				return name;
			}

			public String getTenantId() {
				return tenantId;
			}

			/**
			 * @param name
			 *            the name to set
			 */
			public void setName(String name) {
				this.name = name;
			}

			/**
			 * @param tenantId
			 *            the tenantId to set
			 */
			public void setTenantId(String tenantId) {
				this.tenantId = tenantId;
			}

			@Override
			public String toString() {
				return "Group [name=" + name + ", tenantId=" + tenantId + "]";
			}

		}

		public static final class IpRange implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String cidr;


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

			@Override
			public String toString() {
				return "IpRange [cidr=" + cidr + "]";
			}

		}


		private String id;

		@XmlElement(name = "parent_group_id")
		private String parentGroupId;

		@XmlElement(name = "from_port")
		private Integer fromPort;

		@XmlElement(name = "to_port")
		private Integer toPort;

		@XmlElement(name = "ip_protocol")
		private String ipProtocol;

		@XmlElement(name = "ip_range")
		private IpRange ipRange = new IpRange();

		private Group group;


		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @return the parentGroupId
		 */
		public String getParentGroupId() {
			return parentGroupId;
		}

		/**
		 * @return the fromPort
		 */
		public Integer getFromPort() {
			return fromPort;
		}

		/**
		 * @return the toPort
		 */
		public Integer getToPort() {
			return toPort;
		}

		/**
		 * @return the ipProtocol
		 */
		public String getIpProtocol() {
			return ipProtocol;
		}

		/**
		 * @return the ipRange
		 */
		public IpRange getIpRange() {
			return ipRange;
		}

		/**
		 * @return the group
		 */
		public Group getGroup() {
			return group;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @param parentGroupId
		 *            the parentGroupId to set
		 */
		public void setParentGroupId(String parentGroupId) {
			this.parentGroupId = parentGroupId;
		}

		/**
		 * @param fromPort
		 *            the fromPort to set
		 */
		public void setFromPort(Integer fromPort) {
			this.fromPort = fromPort;
		}

		/**
		 * @param toPort
		 *            the toPort to set
		 */
		public void setToPort(Integer toPort) {
			this.toPort = toPort;
		}

		/**
		 * @param ipProtocol
		 *            the ipProtocol to set
		 */
		public void setIpProtocol(String ipProtocol) {
			this.ipProtocol = ipProtocol;
		}

		/**
		 * @param ipRange
		 *            the ipRange to set
		 */
		public void setIpRange(IpRange ipRange) {
			this.ipRange = ipRange;
		}

		/**
		 * @param group
		 *            the group to set
		 */
		public void setGroup(Group group) {
			this.group = group;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Rule [id=" + id + ", parentGroupId=" + parentGroupId + ", fromPort=" + fromPort + ", toPort=" + toPort
					+ ", ipProtocol=" + ipProtocol + ", ipRange=" + ipRange + ", group=" + group + "]";
		}

	}


	private String id;

	private String name;

	private String description;

	@XmlElement(name = "tenant_id")
	private String tenantId;

	private List<Rule> rules;


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @return the rules
	 */
	public List<Rule> getRules() {
		return rules;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @param rules
	 *            the rules to set
	 */
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SecurityGroup [id=" + id + ", name=" + name + ", description=" + description + ", tenantId=" + tenantId
				+ ", rules=" + rules + "]";
	}

}
