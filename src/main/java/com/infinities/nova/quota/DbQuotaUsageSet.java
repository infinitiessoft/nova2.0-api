package com.infinities.nova.quota;

import javax.xml.bind.annotation.XmlElement;

import com.infinities.nova.db.model.QuotaUsage;

public class DbQuotaUsageSet {

	@XmlElement(name = "metadata_items")
	private QuotaUsage metadataItems;

	@XmlElement(name = "injected_file_content_bytes")
	private QuotaUsage injectedFileContentBytes;

	@XmlElement(name = "injected_files")
	private QuotaUsage injectedFiles;

	private QuotaUsage gigabytes;

	private QuotaUsage ram;

	@XmlElement(name = "floating_ips")
	private QuotaUsage floatingIps;

	private QuotaUsage instances;

	private QuotaUsage volumes;

	private QuotaUsage cores;

	@XmlElement(name = "security_groups")
	private QuotaUsage securityGroups;

	@XmlElement(name = "security_group_rules")
	private QuotaUsage securityGroupRules;

	@XmlElement(name = "injected_file_path_bytes")
	private QuotaUsage injectedFilePathBytes;

	@XmlElement(name = "key_pairs")
	private QuotaUsage keyPairs;

	@XmlElement(name = "fixed_ips")
	private QuotaUsage fixedIps;


	public QuotaUsage getMetadataItems() {
		return metadataItems;
	}

	public void setMetadataItems(QuotaUsage metadataItems) {
		this.metadataItems = metadataItems;
	}

	public QuotaUsage getInjectedFileContentBytes() {
		return injectedFileContentBytes;
	}

	public void setInjectedFileContentBytes(QuotaUsage injectedFileContentBytes) {
		this.injectedFileContentBytes = injectedFileContentBytes;
	}

	public QuotaUsage getInjectedFiles() {
		return injectedFiles;
	}

	public void setInjectedFiles(QuotaUsage injectedFiles) {
		this.injectedFiles = injectedFiles;
	}

	public QuotaUsage getGigabytes() {
		return gigabytes;
	}

	public void setGigabytes(QuotaUsage gigabytes) {
		this.gigabytes = gigabytes;
	}

	public QuotaUsage getRam() {
		return ram;
	}

	public void setRam(QuotaUsage ram) {
		this.ram = ram;
	}

	public QuotaUsage getFloatingIps() {
		return floatingIps;
	}

	public void setFloatingIps(QuotaUsage floatingIps) {
		this.floatingIps = floatingIps;
	}

	public QuotaUsage getInstances() {
		return instances;
	}

	public void setInstances(QuotaUsage instances) {
		this.instances = instances;
	}

	public QuotaUsage getVolumes() {
		return volumes;
	}

	public void setVolumes(QuotaUsage volumes) {
		this.volumes = volumes;
	}

	public QuotaUsage getCores() {
		return cores;
	}

	public void setCores(QuotaUsage cores) {
		this.cores = cores;
	}

	public QuotaUsage getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(QuotaUsage securityGroups) {
		this.securityGroups = securityGroups;
	}

	public QuotaUsage getSecurityGroupRules() {
		return securityGroupRules;
	}

	public void setSecurityGroupRules(QuotaUsage securityGroupRules) {
		this.securityGroupRules = securityGroupRules;
	}

	public QuotaUsage getKeyPairs() {
		return keyPairs;
	}

	public void setKeyPairs(QuotaUsage keyPairs) {
		this.keyPairs = keyPairs;
	}

	public QuotaUsage getInjectedFilePathBytes() {
		return injectedFilePathBytes;
	}

	public void setInjectedFilePathBytes(QuotaUsage injectedFilePathBytes) {
		this.injectedFilePathBytes = injectedFilePathBytes;
	}

	public QuotaUsage getFixedIps() {
		return fixedIps;
	}

	public void setFixedIps(QuotaUsage fixedIps) {
		this.fixedIps = fixedIps;
	}

}
