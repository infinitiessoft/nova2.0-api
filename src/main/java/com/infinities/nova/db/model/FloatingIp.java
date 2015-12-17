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

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "FLOATING_IPS", uniqueConstraints = { @UniqueConstraint(columnNames = { "ADDRESS", "DELETED" }) })
public class FloatingIp extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String address;
	private String projectid;
	private String host;
	private Boolean autoAssigned;
	private String pool;
	private String vInterface;
	private FixedIp fixedIp;


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PROJECT_ID", length = 255)
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	@Column(name = "HOST", length = 255)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name = "AUTO_ASSIGNED")
	public Boolean getAutoAssigned() {
		return autoAssigned;
	}

	public void setAutoAssigned(Boolean autoAssigned) {
		this.autoAssigned = autoAssigned;
	}

	@Column(name = "POOL", length = 255)
	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	@Column(name = "V_INTERFACE", length = 255)
	public String getvInterface() {
		return vInterface;
	}

	public void setvInterface(String vInterface) {
		this.vInterface = vInterface;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIXEDIP_ID")
	public FixedIp getFixedIp() {
		return fixedIp;
	}

	public void setFixedIp(FixedIp fixedIp) {
		this.fixedIp = fixedIp;
	}

}
