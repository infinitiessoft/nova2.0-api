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
public class ImageFromVolume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "id")
	String id;
	@XmlElement(name = "updates_at")
	String updates_at;
	@XmlElement(name = "status")
	String status;
	@XmlElement(name = "display_description")
	String display_description;
	@XmlElement(name = "size")
	String size;
	@XmlElement(name = "volume_type")
	String volume_type;
	@XmlElement(name = "image_id")
	String image_id;
	@XmlElement(name = "container_format")
	String container_format;
	@XmlElement(name = "disk_format")
	String disk_format;
	@XmlElement(name = "image_name")
	String image_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdates_at() {
		return updates_at;
	}

	public void setUpdates_at(String updates_at) {
		this.updates_at = updates_at;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDisplay_description() {
		return display_description;
	}

	public void setDisplay_description(String display_description) {
		this.display_description = display_description;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getVolume_type() {
		return volume_type;
	}

	public void setVolume_type(String volume_type) {
		this.volume_type = volume_type;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
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
