package com.infinities.nova.quota;

import javax.xml.bind.annotation.XmlElement;

public class QuotaUsageSet {

	@XmlElement(name = "metadata_items")
	private Usage metadataItems;

	@XmlElement(name = "injected_file_content_bytes")
	private Usage injectedFileContentBytes;

	@XmlElement(name = "injected_files")
	private Usage injectedFiles;

	private Usage gigabytes;

	private Usage ram;

	@XmlElement(name = "floating_ips")
	private Usage floatingIps;

	private Usage instances;

	private Usage volumes;

	private Usage cores;

	@XmlElement(name = "security_groups")
	private Usage securityGroups;

	@XmlElement(name = "security_group_rules")
	private Usage securityGroupRules;

	@XmlElement(name = "injected_file_path_bytes")
	private Usage injectedFilePathBytes;

	@XmlElement(name = "key_pairs")
	private Usage keyPairs;

	@XmlElement(name = "fixed_ips")
	private Usage fixedIps;


	public Usage getMetadataItems() {
		return metadataItems;
	}

	public void setMetadataItems(Usage metadataItems) {
		this.metadataItems = metadataItems;
	}

	public Usage getInjectedFileContentBytes() {
		return injectedFileContentBytes;
	}

	public void setInjectedFileContentBytes(Usage injectedFileContentBytes) {
		this.injectedFileContentBytes = injectedFileContentBytes;
	}

	public Usage getInjectedFiles() {
		return injectedFiles;
	}

	public void setInjectedFiles(Usage injectedFiles) {
		this.injectedFiles = injectedFiles;
	}

	public Usage getGigabytes() {
		return gigabytes;
	}

	public void setGigabytes(Usage gigabytes) {
		this.gigabytes = gigabytes;
	}

	public Usage getRam() {
		return ram;
	}

	public void setRam(Usage ram) {
		this.ram = ram;
	}

	public Usage getFloatingIps() {
		return floatingIps;
	}

	public void setFloatingIps(Usage floatingIps) {
		this.floatingIps = floatingIps;
	}

	public Usage getInstances() {
		return instances;
	}

	public void setInstances(Usage instances) {
		this.instances = instances;
	}

	public Usage getVolumes() {
		return volumes;
	}

	public void setVolumes(Usage volumes) {
		this.volumes = volumes;
	}

	public Usage getCores() {
		return cores;
	}

	public void setCores(Usage cores) {
		this.cores = cores;
	}

	public Usage getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(Usage securityGroups) {
		this.securityGroups = securityGroups;
	}

	public Usage getSecurityGroupRules() {
		return securityGroupRules;
	}

	public void setSecurityGroupRules(Usage securityGroupRules) {
		this.securityGroupRules = securityGroupRules;
	}

	public Usage getKeyPairs() {
		return keyPairs;
	}

	public void setKeyPairs(Usage keyPairs) {
		this.keyPairs = keyPairs;
	}

	public Usage getInjectedFilePathBytes() {
		return injectedFilePathBytes;
	}

	public void setInjectedFilePathBytes(Usage injectedFilePathBytes) {
		this.injectedFilePathBytes = injectedFilePathBytes;
	}

	public Usage getFixedIps() {
		return fixedIps;
	}

	public void setFixedIps(Usage fixedIps) {
		this.fixedIps = fixedIps;
	}


	public static class Usage {

		private Integer inUse;
		private Integer reserved;

		private Integer limit;
		private Integer remains;

		private Integer maximum;
		private Integer minimum;

		private Integer total;


		public Integer getInUse() {
			return inUse;
		}

		public void setInUse(Integer inUse) {
			this.inUse = inUse;
		}

		public Integer getReserved() {
			return reserved;
		}

		public void setReserved(Integer reserved) {
			this.reserved = reserved;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public Integer getRemains() {
			return remains;
		}

		public void setRemains(Integer remains) {
			this.remains = remains;
		}

		public Integer getMaximum() {
			return maximum;
		}

		public void setMaximum(Integer maximum) {
			this.maximum = maximum;
		}

		public Integer getMinimum() {
			return minimum;
		}

		public void setMinimum(Integer minimum) {
			this.minimum = minimum;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

	}

}
