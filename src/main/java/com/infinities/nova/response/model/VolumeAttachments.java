package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class VolumeAttachments implements Iterable<VolumeAttachment>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "volumeAttachments")
	private List<VolumeAttachment> list;


	/**
	 * @return the list
	 */
	public List<VolumeAttachment> getList() {
		return list;
	}

	@Override
	public Iterator<VolumeAttachment> iterator() {
		return list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VolumeAttachments [list=" + list + "]";
	}

}
