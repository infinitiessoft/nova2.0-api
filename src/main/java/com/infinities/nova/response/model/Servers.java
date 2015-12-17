package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Servers implements Iterable<Server>, Serializable {

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

	public void setList(List<Server> list) {
		this.list = list;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Servers [list=" + list + "]";
	}

}
