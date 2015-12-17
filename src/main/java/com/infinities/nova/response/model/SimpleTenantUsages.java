package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class SimpleTenantUsages implements Iterable<SimpleTenantUsage>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "tenant_usages")
	private List<SimpleTenantUsage> list;


	/**
	 * @return the list
	 */
	public List<SimpleTenantUsage> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleTenantUsage [list=" + list + "]";
	}

	@Override
	public Iterator<SimpleTenantUsage> iterator() {
		return list.iterator();
	}

}
