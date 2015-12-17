package com.infinities.nova.response.model;

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

			@Override
			public String toString() {
				return "IpRange [cidr=" + cidr + "]";
			}

		}


		private String id;

		private String name;

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
		 * @return the name
		 */
		public String getName() {
			return name;
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Rule [id=" + id + ", name=" + name + ", parentGroupId=" + parentGroupId + ", fromPort=" + fromPort
					+ ", toPort=" + toPort + ", ipProtocol=" + ipProtocol + ", ipRange=" + ipRange + ", group=" + group
					+ "]";
		}

	}


	private String id;

	private String name;

	private String description;

	@XmlElement(name = "tenant_id")
	private String tenantId;

	private List<Rule> rules;

	private List<Link> links;


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
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SecurityGroup [id=" + id + ", name=" + name + ", description=" + description + ", tenantId=" + tenantId
				+ ", rules=" + rules + ", links=" + links + "]";
	}

}
