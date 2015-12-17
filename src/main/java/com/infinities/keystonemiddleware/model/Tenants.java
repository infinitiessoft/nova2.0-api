package com.infinities.keystonemiddleware.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Tenants implements Iterable<Tenant>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "tenants")
	private List<Tenant> list;

	@XmlElement(name = "tenants_links")
	private List<Link> links;


	/**
	 * @return the list
	 */
	public List<Tenant> getList() {
		return list;
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tenants [list=" + list + ", links=" + links + "]";
	}

	@Override
	public Iterator<Tenant> iterator() {
		return list.iterator();
	}

}
