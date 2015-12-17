package com.infinities.keystonemiddleware.model.v3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Groups implements Iterable<Group>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "groups")
	private List<Group> list;


	/**
	 * @return the list
	 */
	public List<Group> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Groups [list=" + list + "]";
	}

	@Override
	public Iterator<Group> iterator() {
		return list.iterator();
	}

}
