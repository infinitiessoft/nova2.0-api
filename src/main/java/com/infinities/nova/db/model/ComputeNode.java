package com.infinities.nova.db.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMPUTE_NODES")
public class ComputeNode extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer vcpus;
	private Integer memoryMb;
	private Integer localGb;
	private Integer vcpusUsed;
	private Integer memoryMbUsed;
	private Integer localGbUsed;
	private String hypervisorType;
	private Integer hypervisorVersion;
	private String hypervisorHostname;
	private Integer freeRamMb;
	private Integer freeDiskGb;
	private Integer currentWorkload;
	private Integer runningVms;
	private String cpuInfo;
	private Integer diskAvailableLeast;
	private String hostIp;
	private String supportedInstances;
	private String metrics;
	private String pciStats;
	private String extraResources;
	private String stats;
	private String numaTopology;


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVcpus() {
		return vcpus;
	}

	public void setVcpus(Integer vcpus) {
		this.vcpus = vcpus;
	}

	public Integer getMemoryMb() {
		return memoryMb;
	}

	public void setMemoryMb(Integer memoryMb) {
		this.memoryMb = memoryMb;
	}

	public Integer getLocalGb() {
		return localGb;
	}

	public void setLocalGb(Integer localGb) {
		this.localGb = localGb;
	}

	public Integer getVcpusUsed() {
		return vcpusUsed;
	}

	public void setVcpusUsed(Integer vcpusUsed) {
		this.vcpusUsed = vcpusUsed;
	}

	public Integer getMemoryMbUsed() {
		return memoryMbUsed;
	}

	public void setMemoryMbUsed(Integer memoryMbUsed) {
		this.memoryMbUsed = memoryMbUsed;
	}

	public Integer getLocalGbUsed() {
		return localGbUsed;
	}

	public void setLocalGbUsed(Integer localGbUsed) {
		this.localGbUsed = localGbUsed;
	}

	public String getHypervisorType() {
		return hypervisorType;
	}

	public void setHypervisorType(String hypervisorType) {
		this.hypervisorType = hypervisorType;
	}

	public Integer getHypervisorVersion() {
		return hypervisorVersion;
	}

	public void setHypervisorVersion(Integer hypervisorVersion) {
		this.hypervisorVersion = hypervisorVersion;
	}

	public String getHypervisorHostname() {
		return hypervisorHostname;
	}

	public void setHypervisorHostname(String hypervisorHostname) {
		this.hypervisorHostname = hypervisorHostname;
	}

	public Integer getFreeRamMb() {
		return freeRamMb;
	}

	public void setFreeRamMb(Integer freeRamMb) {
		this.freeRamMb = freeRamMb;
	}

	public Integer getFreeDiskGb() {
		return freeDiskGb;
	}

	public void setFreeDiskGb(Integer freeDiskGb) {
		this.freeDiskGb = freeDiskGb;
	}

	public Integer getCurrentWorkload() {
		return currentWorkload;
	}

	public void setCurrentWorkload(Integer currentWorkload) {
		this.currentWorkload = currentWorkload;
	}

	public Integer getRunningVms() {
		return runningVms;
	}

	public void setRunningVms(Integer runningVms) {
		this.runningVms = runningVms;
	}

	public String getCpuInfo() {
		return cpuInfo;
	}

	public void setCpuInfo(String cpuInfo) {
		this.cpuInfo = cpuInfo;
	}

	public Integer getDiskAvailableLeast() {
		return diskAvailableLeast;
	}

	public void setDiskAvailableLeast(Integer diskAvailableLeast) {
		this.diskAvailableLeast = diskAvailableLeast;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getSupportedInstances() {
		return supportedInstances;
	}

	public void setSupportedInstances(String supportedInstances) {
		this.supportedInstances = supportedInstances;
	}

	public String getMetrics() {
		return metrics;
	}

	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}

	public String getPciStats() {
		return pciStats;
	}

	public void setPciStats(String pciStats) {
		this.pciStats = pciStats;
	}

	public String getExtraResources() {
		return extraResources;
	}

	public void setExtraResources(String extraResources) {
		this.extraResources = extraResources;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getNumaTopology() {
		return numaTopology;
	}

	public void setNumaTopology(String numaTopology) {
		this.numaTopology = numaTopology;
	}

}
