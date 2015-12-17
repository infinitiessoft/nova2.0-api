package com.infinities.keystonemiddleware.model.v3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Policies implements Iterable<Policy>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "policies")
	private List<Policy> list;


	/**
	 * @return the list
	 */
	public List<Policy> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Policies [list=" + list + "]";
	}

	@Override
	public Iterator<Policy> iterator() {
		return list.iterator();
	}

}
