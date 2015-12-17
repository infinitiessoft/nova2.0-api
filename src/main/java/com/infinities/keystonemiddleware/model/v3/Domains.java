package com.infinities.keystonemiddleware.model.v3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Domains implements Iterable<Domain>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "domains")
	private List<Domain> list;


	/**
	 * @return the list
	 */
	public List<Domain> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Domains [list=" + list + "]";
	}

	@Override
	public Iterator<Domain> iterator() {
		return list.iterator();
	}

}
