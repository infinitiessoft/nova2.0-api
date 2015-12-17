package com.infinities.nova.api.openstack.compute.flavors;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.infinities.nova.response.model.Link;

public class MinimalFlavorsTemplate implements Iterable<MinimalFlavor>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "flavors")
	private List<MinimalFlavor> list;

	@XmlElement(name = "flavors_links", nillable = false)
	private List<Link> links;


	/**
	 * @return the list
	 */
	public List<MinimalFlavor> getList() {
		return list;
	}

	@Override
	public Iterator<MinimalFlavor> iterator() {
		return list.iterator();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setList(List<MinimalFlavor> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinimalFlavorsTemplate [list=" + list + "]";
	}

}
