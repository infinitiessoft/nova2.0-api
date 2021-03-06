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
package com.infinities.nova.images.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.infinities.api.openstack.commons.model.Link;

@XmlRootElement(name = "image")
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final class Server implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String id;

		private List<Link> links = new ArrayList<Link>(0);


		public void setId(String id) {
			this.id = id;
		}

		public void setLinks(List<Link> links) {
			this.links = links;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @return the links
		 */
		public List<Link> getLinks() {
			return links;
		}

		@Override
		public String toString() {
			return "Server [id=" + id + ", links=" + links + "]";
		}

	}


	private String id;

	private String status;

	private String name;

	private Integer progress;

	private Integer minRam;

	private Integer minDisk;

	private Calendar created;

	private Calendar updated;

	@XmlElement(name = "OS-EXT-IMG-SIZE:size")
	private Integer size;

	private Map<String, String> metadata;

	private Server server;

	private List<Link> links;


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the progress
	 */
	public Integer getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	/**
	 * @return the minRam
	 */
	public Integer getMinRam() {
		return minRam;
	}

	/**
	 * @param minRam
	 *            the minRam to set
	 */
	public void setMinRam(Integer minRam) {
		this.minRam = minRam;
	}

	/**
	 * @return the minDisk
	 */
	public Integer getMinDisk() {
		return minDisk;
	}

	/**
	 * @param minDisk
	 *            the minDisk to set
	 */
	public void setMinDisk(Integer minDisk) {
		this.minDisk = minDisk;
	}

	/**
	 * @return the created
	 */
	public Calendar getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Calendar created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public Calendar getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the server
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Image [id=" + id + ", status=" + status + ", name=" + name + ", progress=" + progress + ", minRam=" + minRam
				+ ", minDisk=" + minDisk + ", created=" + (created != null ? created.getTime() : null) + ", updated="
				+ (updated != null ? updated.getTime() : null) + ", size=" + size + ", metadata=" + metadata + ", server="
				+ server + ", links=" + links + "]";
	}

}
