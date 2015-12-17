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
package com.infinities.nova.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "INSTANCE_TYPE")
public class InstanceType extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String flavorid;
	private String name;
	private Integer vcpus;
	private Integer memoryMb;
	private Integer rootGb;
	private Integer ephemeralGb;
	private String swap;
	private Float rxtxFactor;
	private Boolean disabled;
	private Integer rxtxQuota;
	private Integer rxtxCap;
	private Boolean isPublic;


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

	@Column(name = "NAME", length = 150)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FLAVORID", length = 50)
	public String getFlavorid() {
		return flavorid;
	}

	public void setFlavorid(String flavorid) {
		this.flavorid = flavorid;
	}

	@Column(name = "VCPUS")
	public Integer getVcpus() {
		return vcpus;
	}

	public void setVcpus(Integer vcpus) {
		this.vcpus = vcpus;
	}

	@Column(name = "MEMORY_MB")
	public Integer getMemoryMb() {
		return memoryMb;
	}

	public void setMemoryMb(Integer memoryMb) {
		this.memoryMb = memoryMb;
	}

	@Column(name = "ROOT_GB")
	public Integer getRootGb() {
		return rootGb;
	}

	public void setRootGb(Integer rootGb) {
		this.rootGb = rootGb;
	}

	@Column(name = "EPHEMERAL_GB")
	public Integer getEphemeralGb() {
		return ephemeralGb;
	}

	public void setEphemeralGb(Integer ephemeralGb) {
		this.ephemeralGb = ephemeralGb;
	}

	@Column(name = "SWAP", length = 50)
	public String getSwap() {
		return swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	@Column(name = "RXTX_FACTOR")
	public Float getRxtxFactor() {
		return rxtxFactor;
	}

	public void setRxtxFactor(Float rxtxFactor) {
		this.rxtxFactor = rxtxFactor;
	}

	@Column(name = "DISABLED")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "RXTX_QUOTA")
	public Integer getRxtxQuota() {
		return rxtxQuota;
	}

	public void setRxtxQuota(Integer rxtxQuota) {
		this.rxtxQuota = rxtxQuota;
	}

	@Column(name = "RXTX_CAP")
	public Integer getRxtxCap() {
		return rxtxCap;
	}

	public void setRxtxCap(Integer rxtxCap) {
		this.rxtxCap = rxtxCap;
	}

	@Column(name = "IS_PUBLIC")
	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

}
