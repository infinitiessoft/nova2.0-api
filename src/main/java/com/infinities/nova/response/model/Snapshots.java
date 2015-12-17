package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Snapshots implements Iterable<Snapshot>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "snapshots")
	private List<Snapshot> list;


	/**
	 * @return the list
	 */
	public List<Snapshot> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Snapshots [list=" + list + "]";
	}

	@Override
	public Iterator<Snapshot> iterator() {
		return list.iterator();
	}

}
