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
package com.infinities.nova.api.openstack.compute.servers;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

public class ServersFilter {

	private List<String> vmState;
	private List<String> taskState;
	private Calendar changesSince;
	private Boolean deleted;
	private String projectId;
	private String userId;
	private Boolean allTenants;
	private String reservationId;
	private String image;
	private String flavor;
	private String ip;
	private String name;
	// private String instanceTypeId;
	private List<String> uuids;
	private String host;
	private Map<String, String> metadata;


	public List<String> getVmState() {
		return vmState;
	}

	public void setVmState(List<String> vmState) {
		this.vmState = vmState;
	}

	public List<String> getTaskState() {
		return taskState;
	}

	public void setTaskState(List<String> taskState) {
		this.taskState = taskState;
	}

	public Calendar getChangesSince() {
		return changesSince;
	}

	public void setChangesSince(Calendar changesSince) {
		this.changesSince = changesSince;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getAllTenants() {
		return allTenants;
	}

	public void setAllTenants(Boolean allTenants) {
		this.allTenants = allTenants;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getIp() {
		if (Strings.isNullOrEmpty(ip)) {
			return null;
		} else {
			return ip.replace(".", "\\.");
		}
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageRef() {
		return image;
	}

	public String getDisplayName() {
		return name;
	}

	public List<String> getUuids() {
		return uuids;
	}

	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

}
