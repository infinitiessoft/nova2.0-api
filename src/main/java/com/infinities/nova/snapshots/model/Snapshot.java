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
import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "snapshot")
public class Snapshot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String status;

	@XmlElement(name = "displayName")
	private String name;

	@XmlElement(name = "displayDescription")
	private String description;

	private String volumeId;

	private Integer size;

	private Calendar createdAt;


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the volumeId
	 */
	public String getVolumeId() {
		return volumeId;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @return the createdAt
	 */
	public Calendar getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param volumeId
	 *            the volumeId to set
	 */
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Snapshot [id=" + id + ", status=" + status + ", displayName=" + name + ", displayDescription=" + description
				+ ", volumeId=" + volumeId + ", size=" + size + ", createdAt=" + createdAt + "]";
	}

}
