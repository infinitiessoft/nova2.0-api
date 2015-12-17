package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class Cloudpipes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "cloudpipes")
	private List<Cloudpipe> list;

	/**
	 * @return the list
	 */
	public List<Cloudpipe> getList() {
		return list;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Flavors [list=" + list + "]";
	}

}

