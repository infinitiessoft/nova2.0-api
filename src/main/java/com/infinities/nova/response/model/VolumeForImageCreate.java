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
package com.infinities.nova.response.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "os-volume_upload_image")
public class VolumeForImageCreate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String volumeId;
	String tenantId;


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	@XmlElement(name = "force")
	private Boolean force;


	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}


	@XmlElement(name = "container_format")
	String container_format;
	@XmlElement(name = "disk_format")
	String disk_format;
	@XmlElement(name = "image_name")
	String image_name;


	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getContainer_format() {
		return container_format;
	}

	public void setContainer_format(String container_format) {
		this.container_format = container_format;
	}

	public String getDisk_format() {
		return disk_format;
	}

	public void setDisk_format(String disk_format) {
		this.disk_format = disk_format;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

}
