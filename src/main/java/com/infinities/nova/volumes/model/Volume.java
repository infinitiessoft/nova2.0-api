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
package com.infinities.nova.volumes.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.infinities.nova.servers.volumes.model.VolumeAttachment;

@XmlRootElement(name = "volume")
public class Volume implements Serializable {

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

	private String availabilityZone;

	private String volumeType;

	private String snapshotId;

	private List<VolumeAttachment> attachments;

	private Map<String, String> metadata;

	private Calendar createdAt;

	private Integer size;


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
	 * @return the availabilityZone
	 */
	public String getAvailabilityZone() {
		return availabilityZone;
	}

	/**
	 * @return the volumeType
	 */
	public String getVolumeType() {
		return volumeType;
	}

	/**
	 * @return the snapshotId
	 */
	public String getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @return the attachments
	 */
	public List<VolumeAttachment> getAttachments() {
		return attachments;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * @return the createdAt
	 */
	public Calendar getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
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
	 * @param availabilityZone
	 *            the availabilityZone to set
	 */
	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	/**
	 * @param volumeType
	 *            the volumeType to set
	 */
	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	/**
	 * @param snapshotId
	 *            the snapshotId to set
	 */
	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	/**
	 * @param attachments
	 *            the attachments to set
	 */
	public void setAttachments(List<VolumeAttachment> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

}
