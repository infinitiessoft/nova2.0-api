package com.infinities.nova.db.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jersey.repackaged.com.google.common.collect.Maps;

import org.dasein.cloud.compute.VirtualMachine;
import org.dasein.cloud.compute.VmState;
import org.dasein.cloud.network.IPVersion;
import org.dasein.cloud.network.RawAddress;
import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Strings;
import com.infinities.nova.openstack.common.policy.Target;

@Entity
@Table(name = "INSTANCES")
public class Instance extends AbstractModel implements Target {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<VmState, String> STATUS_MAP;
	static {
		Map<VmState, String> status = new EnumMap<VmState, String>(VmState.class);
		status.put(VmState.RUNNING, "ACTIVE");
		status.put(VmState.PENDING, "BUILD");
		status.put(VmState.TERMINATED, "DELETED");
		status.put(VmState.SUSPENDED, "SUSPENDED");
		status.put(VmState.PAUSED, "PAUSED");
		status.put(VmState.STOPPED, "STOPPED");
		status.put(VmState.STOPPING, "STOPPING");
		status.put(VmState.PAUSING, "PAUSING");
		status.put(VmState.SUSPENDING, "SUSPENDING");
		status.put(VmState.ERROR, "ERROR");
		status.put(VmState.REBOOTING, "REBOOT");
		STATUS_MAP = Maps.immutableEnumMap(status);
	}

	private String id;

	private String name;

	private List<Address> addresses;

	private String imageId;

	private String accessIpV4;

	private String accessIpV6;

	private String configDrive;

	private String status;

	private Integer progress;

	private Fault fault;

	private String tenantId;

	private String userId;

	private String keyName;

	private String hostId;

	private Map<String, String> metadata;

	// @CollectionTable(name = "securityGroups", joinColumns = @JoinColumn(name
	// = "instance_id"))
	// @Column(name = "securityGroup")
	private List<String> securityGroups;

	private String taskState;

	private String powerState;

	private String vmState;

	private String host;

	private String instanceName;

	private String hypervisorHostname;

	private String diskConfig;

	private String availabilityZone;

	private Calendar launchedAt;

	private Calendar terminatedAt;

	private List<String> volumesAttached;

	private String instanceId;

	private String adminPass;

	private String flavorId;


	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instance", cascade = CascadeType.REMOVE)
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Column(name = "IMAGE_ID", length = 50)
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Column(name = "ACCESS_IP_V4", length = 50)
	public String getAccessIpV4() {
		return accessIpV4;
	}

	public void setAccessIpV4(String accessIpV4) {
		this.accessIpV4 = accessIpV4;
	}

	@Column(name = "ACCESS_IP_V6", length = 50)
	public String getAccessIpV6() {
		return accessIpV6;
	}

	public void setAccessIpV6(String accessIpV6) {
		this.accessIpV6 = accessIpV6;
	}

	@Column(name = "CONFIG_DRIVE", length = 50)
	public String getConfigDrive() {
		return configDrive;
	}

	public void setConfigDrive(String configDrive) {
		this.configDrive = configDrive;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "PROGRESS")
	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER, orphanRemoval = true)
	// @JoinColumn(name="USER_ID", nullable=false)
			@PrimaryKeyJoinColumn
			// @JoinColumn(name = "INSTANCE_ID")
			public
			Fault getFault() {
		return fault;
	}

	public void setFault(Fault fault) {
		this.fault = fault;
	}

	@Column(name = "TENANT_ID", length = 50)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Column(name = "USER_ID", length = 50)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "KEY_NAME", length = 50)
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Column(name = "HOST_ID", length = 100)
	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@ElementCollection
	@Column(name = "METADATA_ID")
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@ElementCollection
	@Column(name = "SECURITY_GROUP_ID")
	public List<String> getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(List<String> securityGroups) {
		this.securityGroups = securityGroups;
	}

	@Column(name = "TASK_STATE", length = 50)
	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	@Column(name = "POWER_STATE", length = 50)
	public String getPowerState() {
		return powerState;
	}

	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}

	@Column(name = "VM_STATE", length = 50)
	public String getVmState() {
		return vmState;
	}

	public void setVmState(String vmState) {
		this.vmState = vmState;
	}

	@Column(name = "HOST", length = 50)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "INSTANCE_NAME", length = 50)
	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	@Column(name = "HYPERVISOR_HOSTNAME", length = 50)
	public String getHypervisorHostname() {
		return hypervisorHostname;
	}

	public void setHypervisorHostname(String hypervisorHostname) {
		this.hypervisorHostname = hypervisorHostname;
	}

	@Column(name = "DISK_CONFIG", length = 50)
	public String getDiskConfig() {
		return diskConfig;
	}

	public void setDiskConfig(String diskConfig) {
		this.diskConfig = diskConfig;
	}

	@Column(name = "AVAILABILITY_ZONE", length = 50)
	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	@Column(name = "LAUNCHED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLaunchedAt() {
		return launchedAt;
	}

	public void setLaunchedAt(Calendar launchedAt) {
		this.launchedAt = launchedAt;
	}

	@Column(name = "TERMINATED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTerminatedAt() {
		return terminatedAt;
	}

	public void setTerminatedAt(Calendar terminatedAt) {
		this.terminatedAt = terminatedAt;
	}

	@ElementCollection
	@Column(name = "VOLUME_ATTACHED_ID")
	public List<String> getVolumesAttached() {
		return volumesAttached;
	}

	public void setVolumesAttached(List<String> volumesAttached) {
		this.volumesAttached = volumesAttached;
	}

	@Column(name = "INSTANCE_ID", length = 50)
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Column(name = "ADMIN_PASS", length = 255)
	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	@Column(name = "FLAVOR_ID", length = 100)
	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public static Instance toInstance(VirtualMachine vm) {
		Instance instance = new Instance();
		List<Address> addresses = new ArrayList<Address>();
		String ipv4 = null;
		String ipv6 = null;
		for (RawAddress raw : vm.getPublicAddresses()) {
			Address address = new Address();
			address.setAddr(raw.getIpAddress());
			address.setIpVersion(raw.getVersion().toString());
			address.setNetwork("public");
			addresses.add(address);
			if (Strings.isNullOrEmpty(ipv4) && IPVersion.IPV4.equals(raw.getVersion())) {
				ipv4 = raw.getIpAddress();
			}
			if (Strings.isNullOrEmpty(ipv6) && IPVersion.IPV6.equals(raw.getVersion())) {
				ipv6 = raw.getIpAddress();
			}
		}
		for (RawAddress raw : vm.getPrivateAddresses()) {
			Address address = new Address();
			address.setAddr(raw.getIpAddress());
			address.setIpVersion(raw.getVersion().toString());
			address.setNetwork("private");
			addresses.add(address);
			if (Strings.isNullOrEmpty(ipv4) && IPVersion.IPV4.equals(raw.getVersion())) {
				ipv4 = raw.getIpAddress();
			}
			if (Strings.isNullOrEmpty(ipv6) && IPVersion.IPV6.equals(raw.getVersion())) {
				ipv6 = raw.getIpAddress();
			}
		}
		instance.setAccessIpV4(ipv4);
		instance.setAccessIpV6(ipv6);

		instance.setAddresses(addresses);
		instance.setTenantId(vm.getProviderOwnerId());
		instance.setInstanceId(vm.getProviderVirtualMachineId());
		instance.setName(vm.getName());
		instance.setMetadata(vm.getTags());
		instance.setTaskState(vm.getTags().get("OS-EXT-STS:task_state"));
		instance.setHostId(vm.getTags().get("host"));
		instance.setImageId(vm.getProviderMachineImageId());
		instance.setFlavorId(vm.getProductId());
		instance.setAdminPass(vm.getRootPassword());
		instance.setProgress(0);
		if (vm.getProviderShellKeyIds() != null && vm.getProviderShellKeyIds().length > 0) {
			instance.setKeyName(vm.getProviderShellKeyIds()[0]);
		}

		instance.setVmState(STATUS_MAP.get(vm.getCurrentState()).toLowerCase());
		instance.setStatus(STATUS_MAP.get(vm.getCurrentState()));
		if (vm.getCreationTimestamp() > 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(vm.getCreationTimestamp());
			instance.setCreatedAt(calendar);
		}

		return instance;

	}

}
