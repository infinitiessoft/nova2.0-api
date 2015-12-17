package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class VolumeTypes implements Iterable<VolumeType>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "volume-types")
	private List<VolumeType> list;


	/**
	 * @return the list
	 */
	public List<VolumeType> getList() {
		return list;
	}

	@Override
	public Iterator<VolumeType> iterator() {
		return list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VolumeTypes [list=" + list + "]";
	}

}
