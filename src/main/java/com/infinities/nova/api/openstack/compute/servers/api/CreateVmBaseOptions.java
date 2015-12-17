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
package com.infinities.nova.api.openstack.compute.servers.api;

import java.util.Map;
import java.util.UUID;

import com.infinities.nova.compute.PowerState;
import com.infinities.nova.compute.VmStates;

public class CreateVmBaseOptions {

	private UUID reservationId;
	private String imageRef;
	private String kernelId;
	private String ramDiskId;
	private int powerState = PowerState.NOSTATE;
	private String vmState = VmStates.BUILDING;
	private boolean configDrive;
	private String userId;
	private String projectId;
	private String instanceTypeId;
	private int memoryMb;
	private int vpus;
	private int rootGb;
	private int ephemeralGb;
	private String displayName;
	private String displayDescription;
	private String userData;
	private String keyName;
	private String keyData;
	private boolean locked = false;
	private Map<String, String> metadata;
	private String accessIpV4;
	private String accessIpV6;
	private String availabilityZone;
	private String rootDeviceName;
	private int progress = 0;
	private String pciRequestInfo;
	private String numaTopology;
	private Map<String, String> systemMetadata;
	private String osType;
	private String architecture;
	private String vmMode;
	private boolean autoDiskConfig;


	public UUID getReservationId() {
		return reservationId;
	}

	public void setReservationId(UUID reservationId) {
		this.reservationId = reservationId;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public String getKernelId() {
		return kernelId;
	}

	public void setKernelId(String kernelId) {
		this.kernelId = kernelId;
	}

	public String getRamDiskId() {
		return ramDiskId;
	}

	public void setRamDiskId(String ramDiskId) {
		this.ramDiskId = ramDiskId;
	}

	public int getPowerState() {
		return powerState;
	}

	public void setPowerState(int powerState) {
		this.powerState = powerState;
	}

	public String getVmState() {
		return vmState;
	}

	public void setVmState(String vmState) {
		this.vmState = vmState;
	}

	public boolean isConfigDrive() {
		return configDrive;
	}

	public void setConfigDrive(boolean configDrive) {
		this.configDrive = configDrive;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getInstanceTypeId() {
		return instanceTypeId;
	}

	public void setInstanceTypeId(String instanceTypeId) {
		this.instanceTypeId = instanceTypeId;
	}

	public int getMemoryMb() {
		return memoryMb;
	}

	public void setMemoryMb(int memoryMb) {
		this.memoryMb = memoryMb;
	}

	public int getVpus() {
		return vpus;
	}

	public void setVpus(int vpus) {
		this.vpus = vpus;
	}

	public int getRootGb() {
		return rootGb;
	}

	public void setRootGb(int rootGb) {
		this.rootGb = rootGb;
	}

	public int getEphemeralGb() {
		return ephemeralGb;
	}

	public void setEphemeralGb(int ephemeralGb) {
		this.ephemeralGb = ephemeralGb;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyData() {
		return keyData;
	}

	public void setKeyData(String keyData) {
		this.keyData = keyData;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getAccessIpV4() {
		return accessIpV4;
	}

	public void setAccessIpV4(String accessIpV4) {
		this.accessIpV4 = accessIpV4;
	}

	public String getAccessIpV6() {
		return accessIpV6;
	}

	public void setAccessIpV6(String accessIpV6) {
		this.accessIpV6 = accessIpV6;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public void setRootDeviceName(String rootDeviceName) {
		this.rootDeviceName = rootDeviceName;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getPciRequestInfo() {
		return pciRequestInfo;
	}

	public void setPciRequestInfo(String pciRequestInfo) {
		this.pciRequestInfo = pciRequestInfo;
	}

	public String getNumaTopology() {
		return numaTopology;
	}

	public void setNumaTopology(String numaTopology) {
		this.numaTopology = numaTopology;
	}

	public Map<String, String> getSystemMetadata() {
		return systemMetadata;
	}

	public void setSystemMetadata(Map<String, String> systemMetadata) {
		this.systemMetadata = systemMetadata;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getArchitecture() {
		return architecture;
	}

	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}

	public String getVmMode() {
		return vmMode;
	}

	public void setVmMode(String vmMode) {
		this.vmMode = vmMode;
	}

	public boolean isAutoDiskConfig() {
		return autoDiskConfig;
	}

	public void setAutoDiskConfig(boolean autoDiskConfig) {
		this.autoDiskConfig = autoDiskConfig;
	}

}
