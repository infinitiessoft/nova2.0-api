package com.infinities.nova.api.openstack.compute.servers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.infinities.nova.response.model.Link;
import com.infinities.nova.response.model.Server;

public class ServersTemplate implements Iterable<Server>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "servers")
	private List<Server> list;

	@XmlElement(name = "servers_links")
	private List<Link> links;


	/**
	 * @return the list
	 */
	public List<Server> getList() {
		return list;
	}

	@Override
	public Iterator<Server> iterator() {
		return list.iterator();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setList(List<Server> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServersTemplate [list=" + list + "]";
	}

}
