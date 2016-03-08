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
package com.infinities.nova.quota.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "quota_set")
public class QuotaSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	@XmlElement(name = "metadata_items")
	private Integer metadataItems;

	@XmlElement(name = "injected_file_content_bytes")
	private Integer injectedFileContentBytes;

	@XmlElement(name = "injected_files")
	private Integer injectedFiles;

	private Integer gigabytes;

	private Integer ram;

	@XmlElement(name = "floating_ips")
	private Integer floatingIps;

	private Integer instances;

	private Integer volumes;

	private Integer cores;

	@XmlElement(name = "security_groups")
	private Integer securityGroups;

	@XmlElement(name = "security_group_rules")
	private Integer securityGroupRules;

	@XmlElement(name = "injected_file_path_bytes")
	private Integer injectedFilePathBytes;

	@XmlElement(name = "key_pairs")
	private Integer keyPairs;

	@XmlElement(name = "fixed_ips")
	private Integer fixedIps;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMetadataItems() {
		return metadataItems;
	}

	public void setMetadataItems(Integer metadataItems) {
		this.metadataItems = metadataItems;
	}

	public Integer getInjectedFileContentBytes() {
		return injectedFileContentBytes;
	}

	public void setInjectedFileContentBytes(Integer injectedFileContentBytes) {
		this.injectedFileContentBytes = injectedFileContentBytes;
	}

	public Integer getInjectedFiles() {
		return injectedFiles;
	}

	public void setInjectedFiles(Integer injectedFiles) {
		this.injectedFiles = injectedFiles;
	}

	public Integer getGigabytes() {
		return gigabytes;
	}

	public void setGigabytes(Integer gigabytes) {
		this.gigabytes = gigabytes;
	}

	public Integer getRam() {
		return ram;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}

	public Integer getFloatingIps() {
		return floatingIps;
	}

	public void setFloatingIps(Integer floatingIps) {
		this.floatingIps = floatingIps;
	}

	public Integer getInstances() {
		return instances;
	}

	public void setInstances(Integer instances) {
		this.instances = instances;
	}

	public Integer getVolumes() {
		return volumes;
	}

	public void setVolumes(Integer volumes) {
		this.volumes = volumes;
	}

	public Integer getCores() {
		return cores;
	}

	public void setCores(Integer cores) {
		this.cores = cores;
	}

	public Integer getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(Integer securityGroups) {
		this.securityGroups = securityGroups;
	}

	public Integer getSecurityGroupRules() {
		return securityGroupRules;
	}

	public void setSecurityGroupRules(Integer securityGroupRules) {
		this.securityGroupRules = securityGroupRules;
	}

	public Integer getKeyPairs() {
		return keyPairs;
	}

	public void setKeyPairs(Integer keyPairs) {
		this.keyPairs = keyPairs;
	}

	public Integer getInjectedFilePathBytes() {
		return injectedFilePathBytes;
	}

	public void setInjectedFilePathBytes(Integer injectedFilePathBytes) {
		this.injectedFilePathBytes = injectedFilePathBytes;
	}

	public Integer getFixedIps() {
		return fixedIps;
	}

	public void setFixedIps(Integer fixedIps) {
		this.fixedIps = fixedIps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cores == null) ? 0 : cores.hashCode());
		result = prime * result + ((fixedIps == null) ? 0 : fixedIps.hashCode());
		result = prime * result + ((floatingIps == null) ? 0 : floatingIps.hashCode());
		result = prime * result + ((gigabytes == null) ? 0 : gigabytes.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((injectedFileContentBytes == null) ? 0 : injectedFileContentBytes.hashCode());
		result = prime * result + ((injectedFilePathBytes == null) ? 0 : injectedFilePathBytes.hashCode());
		result = prime * result + ((injectedFiles == null) ? 0 : injectedFiles.hashCode());
		result = prime * result + ((instances == null) ? 0 : instances.hashCode());
		result = prime * result + ((keyPairs == null) ? 0 : keyPairs.hashCode());
		result = prime * result + ((metadataItems == null) ? 0 : metadataItems.hashCode());
		result = prime * result + ((ram == null) ? 0 : ram.hashCode());
		result = prime * result + ((securityGroupRules == null) ? 0 : securityGroupRules.hashCode());
		result = prime * result + ((securityGroups == null) ? 0 : securityGroups.hashCode());
		result = prime * result + ((volumes == null) ? 0 : volumes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuotaSet other = (QuotaSet) obj;
		if (cores == null) {
			if (other.cores != null)
				return false;
		} else if (!cores.equals(other.cores))
			return false;
		if (fixedIps == null) {
			if (other.fixedIps != null)
				return false;
		} else if (!fixedIps.equals(other.fixedIps))
			return false;
		if (floatingIps == null) {
			if (other.floatingIps != null)
				return false;
		} else if (!floatingIps.equals(other.floatingIps))
			return false;
		if (gigabytes == null) {
			if (other.gigabytes != null)
				return false;
		} else if (!gigabytes.equals(other.gigabytes))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (injectedFileContentBytes == null) {
			if (other.injectedFileContentBytes != null)
				return false;
		} else if (!injectedFileContentBytes.equals(other.injectedFileContentBytes))
			return false;
		if (injectedFilePathBytes == null) {
			if (other.injectedFilePathBytes != null)
				return false;
		} else if (!injectedFilePathBytes.equals(other.injectedFilePathBytes))
			return false;
		if (injectedFiles == null) {
			if (other.injectedFiles != null)
				return false;
		} else if (!injectedFiles.equals(other.injectedFiles))
			return false;
		if (instances == null) {
			if (other.instances != null)
				return false;
		} else if (!instances.equals(other.instances))
			return false;
		if (keyPairs == null) {
			if (other.keyPairs != null)
				return false;
		} else if (!keyPairs.equals(other.keyPairs))
			return false;
		if (metadataItems == null) {
			if (other.metadataItems != null)
				return false;
		} else if (!metadataItems.equals(other.metadataItems))
			return false;
		if (ram == null) {
			if (other.ram != null)
				return false;
		} else if (!ram.equals(other.ram))
			return false;
		if (securityGroupRules == null) {
			if (other.securityGroupRules != null)
				return false;
		} else if (!securityGroupRules.equals(other.securityGroupRules))
			return false;
		if (securityGroups == null) {
			if (other.securityGroups != null)
				return false;
		} else if (!securityGroups.equals(other.securityGroups))
			return false;
		if (volumes == null) {
			if (other.volumes != null)
				return false;
		} else if (!volumes.equals(other.volumes))
			return false;
		return true;
	}

}
