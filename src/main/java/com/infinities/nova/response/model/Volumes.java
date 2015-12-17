package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Volumes implements Iterable<Volume>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "volumes")
	private List<Volume> list;


	/**
	 * @return the list
	 */
	public List<Volume> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Volumes [list=" + list + "]";
	}

	@Override
	public Iterator<Volume> iterator() {
		return list.iterator();
	}

}
