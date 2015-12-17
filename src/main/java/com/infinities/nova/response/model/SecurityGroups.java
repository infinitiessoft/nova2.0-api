package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class SecurityGroups implements Iterable<SecurityGroup>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "security_groups")
	private List<SecurityGroup> list;


	/**
	 * @return the list
	 */
	public List<SecurityGroup> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SecurityGroups [list=" + list + "]";
	}

	@Override
	public Iterator<SecurityGroup> iterator() {
		return list.iterator();
	}

}
