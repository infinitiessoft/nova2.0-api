package com.infinities.nova.response.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Version implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String updated;
	private List<Link> links = new ArrayList<Link>(0);
	@XmlElement(name = "media-types", nillable = false)
	private List<MediaType> mediaTypes = new ArrayList<MediaType>(0);


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public List<MediaType> getMediaTypes() {
		return mediaTypes;
	}

	public void setMediaTypes(List<MediaType> mediaTypes) {
		this.mediaTypes = mediaTypes;
	}

	@Override
	public Version clone() throws CloneNotSupportedException {
		Version clone = (Version) super.clone();

		clone.links = new ArrayList<Link>(this.links.size());
		for (int i = 0; i < links.size(); i++) {
			Link link = links.get(i).clone();
			clone.links.add(link);
		}

		clone.mediaTypes = new ArrayList<MediaType>(this.mediaTypes.size());
		for (int i = 0; i < mediaTypes.size(); i++) {
			MediaType mediaType = mediaTypes.get(i).clone();
			clone.mediaTypes.add(mediaType);
		}

		return clone;
	}

	@Override
	public String toString() {
		return "Version [id=" + id + ", status=" + status + ", updated=" + updated + ", links=" + links + ", mediaTypes="
				+ mediaTypes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((mediaTypes == null) ? 0 : mediaTypes.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (mediaTypes == null) {
			if (other.mediaTypes != null)
				return false;
		} else if (!mediaTypes.equals(other.mediaTypes))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}

}
