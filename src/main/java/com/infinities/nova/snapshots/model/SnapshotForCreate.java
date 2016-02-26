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
package com.infinities.nova.snapshots.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "snapshot")
public class SnapshotForCreate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "volume_id")
	private String volumeId;

	private Boolean force;

	@XmlElement(name = "display_name")
	private String name;

	@XmlElement(name = "display_description")
	private String description;


	/**
	 * @return the volumeId
	 */
	public String getVolumeId() {
		return volumeId;
	}

	/**
	 * @param volumeId
	 *            the volumeId to set
	 */
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	/**
	 * @return the force
	 */
	public Boolean getForce() {
		return force;
	}

	/**
	 * @param force
	 *            the force to set
	 */
	public void setForce(Boolean force) {
		this.force = force;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SnapshotForCreate [volumeId=" + volumeId + ", force=" + force + ", name=" + name + ", description="
				+ description + "]";
	}

}