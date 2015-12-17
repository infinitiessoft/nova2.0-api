package com.infinities.nova.api.openstack.compute.images;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.infinities.nova.response.model.Link;

public class MinimalImagesTemplate implements Iterable<MinimalImage>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "images")
	private List<MinimalImage> list;

	@XmlElement(name = "images_links", nillable = false)
	private List<Link> links;


	/**
	 * @return the list
	 */
	public List<MinimalImage> getList() {
		return list;
	}

	@Override
	public Iterator<MinimalImage> iterator() {
		return list.iterator();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setList(List<MinimalImage> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinimalImagesTemplate [list=" + list + "]";
	}

}
