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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.infinities.api.openstack.commons.model.Link;
import com.infinities.nova.securitygroups.model.SecurityGroup;

@XmlRootElement(name = "server")
public class Server implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final class Image implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private List<Link> links;


		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List<Link> getLinks() {
			return links;
		}

		public void setLinks(List<Link> links) {
			this.links = links;
		}

	}

	public static final class Flavor implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private List<Link> links;


		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List<Link> getLinks() {
			return links;
		}

		public void setLinks(List<Link> links) {
			this.links = links;
		}

	}

	public static final class Addresses implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		public static final class Address implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@XmlElement(name = "OS-EXT-IPS-MAC:mac_addr")
			private String macAddr;

			private int version;

			private String addr;

			@XmlElement(name = "OS-EXT-IPS:type")
			private String type;


			/**
			 * @return the macAddr
			 */
			public String getMacAddr() {
				return macAddr;
			}

			/**
			 * @return the version
			 */
			public int getVersion() {
				return version;
			}

			/**
			 * @return the addr
			 */
			public String getAddr() {
				return addr;
			}

			/**
			 * @return the type
			 */
			public String getType() {
				return type;
			}

			/**
			 * @param version
			 *            the version to set
			 */
			public void setVersion(int version) {
				this.version = version;
			}

			/**
			 * @param addr
			 *            the addr to set
			 */
			public void setAddr(String addr) {
				this.addr = addr;
			}

			/**
			 * @param type
			 *            the type to set
			 */
			public void setType(String type) {
				this.type = type;
			}

			/**
			 * @param macAddr
			 *            the mac addr to set
			 */
			public void setMacAddr(String macAddr) {
				this.macAddr = macAddr;
			}
		}


		private final Map<String, List<Address>> addresses = new HashMap<String, List<Address>>();


		@JsonAnySetter
		public void add(String key, List<Address> value) {
			addresses.put(key, value);
		}

		/**
		 * @return the ip address List Map
		 */
		public Map<String, List<Address>> getAddresses() {
			return addresses;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Addresses List Map [" + addresses + "]";
		}

	}

	public static final class Fault {

		private Integer code;

		private String message;

		private String details;

		private Calendar created;


		/**
		 * @return the code
		 */
		public Integer getCode() {
			return code;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @return the details
		 */
		public String getDetails() {
			return details;
		}

		/**
		 * @return the created
		 */
		public Calendar getCreated() {
			return created;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setDetails(String details) {
			this.details = details;
		}

		public void setCreated(Calendar created) {
			this.created = created;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Fault [code=" + code + ", message=" + message + ", details=" + details + ", created=" + created + "]";
		}

	}


	private String id;

	private String name;

	private Addresses addresses;

	private List<Link> links;

	private Image image;

	private Flavor flavor;

	private String accessIPv4;

	private String accessIPv6;

	@XmlElement(name = "config_drive")
	private String configDrive;

	private String status;

	private Integer progress;

	private Fault fault;

	@XmlElement(name = "tenant_id")
	private String tenantId;

	@XmlElement(name = "user_id")
	private String userId;

	@XmlElement(name = "key_name")
	private String keyName;

	private String hostId;

	private Calendar updated;

	private Calendar created;

	private Map<String, String> metadata;

	@XmlElement(name = "security_groups")
	private List<SecurityGroup> securityGroups;

	@XmlElement(name = "OS-EXT-STS:task_state")
	private String taskState;

	@XmlElement(name = "OS-EXT-STS:power_state")
	private String powerState;

	@XmlElement(name = "OS-EXT-STS:vm_state")
	private String vmState;

	@XmlElement(name = "OS-EXT-SRV-ATTR:host")
	private String host;

	@XmlElement(name = "OS-EXT-SRV-ATTR:instance_name")
	private String instanceName;

	@XmlElement(name = "OS-EXT-SRV-ATTR:hypervisor_hostname")
	private String hypervisorHostname;

	@XmlElement(name = "OS-DCF:diskConfig")
	private String diskConfig;

	@XmlElement(name = "OS-EXT-AZ:availability_zone")
	private String availabilityZone;

	@XmlElement(name = "OS-SRV-USG:launched_at")
	private String launchedAt;

	@XmlElement(name = "OS-SRV-USG:terminated_at")
	private String terminatedAt;

	@XmlElement(name = "os-extended-volumes:volumes_attached")
	private List<String> osExtendedVolumesAttached;

	private String uuid;

	private String adminPass;


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
	 * @return the addresses
	 */
	public Addresses getAddresses() {
		return addresses;
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the flavor
	 */
	public Flavor getFlavor() {
		return flavor;
	}

	/**
	 * @param flavor
	 *            the flavor to set
	 */
	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}

	/**
	 * @return the accessIPv4
	 */
	public String getAccessIPv4() {
		return accessIPv4;
	}

	/**
	 * @return the accessIPv6
	 */
	public String getAccessIPv6() {
		return accessIPv6;
	}

	/**
	 * @return the configDrive
	 */
	public String getConfigDrive() {
		return configDrive;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return progress;
	}

	/**
	 * @return the fault
	 */
	public Fault getFault() {
		return fault;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * @return the updated
	 */
	public Calendar getUpdated() {
		return updated;
	}

	/**
	 * @return the created
	 */
	public Calendar getCreated() {
		return created;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * @return the securityGroups
	 */
	public List<SecurityGroup> getSecurityGroups() {
		return securityGroups;
	}

	/**
	 * @return the taskState
	 */
	public String getTaskState() {
		return taskState;
	}

	/**
	 * @return the powerState
	 */
	public String getPowerState() {
		return powerState;
	}

	/**
	 * @return the vmState
	 */
	public String getVmState() {
		return vmState;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @return the hypervisorHostname
	 */
	public String getHypervisorHostname() {
		return hypervisorHostname;
	}

	/**
	 * @return the diskConfig
	 */
	public String getDiskConfig() {
		return diskConfig;
	}

	/**
	 * @return the availabilityZone
	 */
	public String getAvailabilityZone() {
		return availabilityZone;
	}

	/**
	 * @return the launchedAt
	 */
	public String getLaunchedAt() {
		return launchedAt;
	}

	/**
	 * @return the terminatedAt
	 */
	public String getTerminatedAt() {
		return terminatedAt;
	}

	/**
	 * @return the osExtendedVolumesAttached
	 */
	public List<String> getOsExtendedVolumesAttached() {
		return osExtendedVolumesAttached;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @return the adminPass
	 */
	public String getAdminPass() {
		return adminPass;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddresses(Addresses addresses) {
		this.addresses = addresses;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setAccessIPv4(String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}

	public void setAccessIPv6(String accessIPv6) {
		this.accessIPv6 = accessIPv6;
	}

	public void setConfigDrive(String configDrive) {
		this.configDrive = configDrive;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public void setFault(Fault fault) {
		this.fault = fault;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public void setSecurityGroups(List<SecurityGroup> securityGroups) {
		this.securityGroups = securityGroups;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}

	public void setVmState(String vmState) {
		this.vmState = vmState;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public void setHypervisorHostname(String hypervisorHostname) {
		this.hypervisorHostname = hypervisorHostname;
	}

	public void setDiskConfig(String diskConfig) {
		this.diskConfig = diskConfig;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	public void setLaunchedAt(String launchedAt) {
		this.launchedAt = launchedAt;
	}

	public void setTerminatedAt(String terminatedAt) {
		this.terminatedAt = terminatedAt;
	}

	public void setOsExtendedVolumesAttached(List<String> osExtendedVolumesAttached) {
		this.osExtendedVolumesAttached = osExtendedVolumesAttached;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Server [id=" + id + ", name=" + name + ", addresses=" + addresses + ", links=" + links + ", image=" + image
				+ ", flavor=" + flavor + ", accessIPv4=" + accessIPv4 + ", accessIPv6=" + accessIPv6 + ", configDrive="
				+ configDrive + ", status=" + status + ", progress=" + progress + ", fault=" + fault + ", tenantId="
				+ tenantId + ", userId=" + userId + ", keyName=" + keyName + ", hostId=" + hostId + ", updated=" + updated
				+ ", created=" + created + ", metadata=" + metadata + ", securityGroups=" + securityGroups + ", taskState="
				+ taskState + ", powerState=" + powerState + ", vmState=" + vmState + ", host=" + host + ", instanceName="
				+ instanceName + ", hypervisorHostname=" + hypervisorHostname + ", diskConfig=" + diskConfig
				+ ", availabilityZone=" + availabilityZone + ", launchedAt=" + launchedAt + ", terminatedAt=" + ", "
				+ "osExtendedVolumesAttached=" + osExtendedVolumesAttached + ", uuid=" + uuid + ", adminPass=" + adminPass
				+ "]";
	}

}
