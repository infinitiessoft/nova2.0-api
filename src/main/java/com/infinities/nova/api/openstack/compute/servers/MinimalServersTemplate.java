package com.infinities.nova.api.openstack.compute.servers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.infinities.nova.response.model.Link;

public class MinimalServersTemplate implements Iterable<MinimalServer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "servers")
	private List<MinimalServer> list;

	@XmlElement(name = "servers_links")
	private List<Link> links;


	/**
	 * @return the list
	 */
	public List<MinimalServer> getList() {
		return list;
	}

	@Override
	public Iterator<MinimalServer> iterator() {
		return list.iterator();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setList(List<MinimalServer> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinimalServersTemplate [list=" + list + "]";
	}

}
