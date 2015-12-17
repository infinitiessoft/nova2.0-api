package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Extensions implements Iterable<Extension>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "extensions")
	private List<Extension> list;


	/**
	 * @return the list
	 */
	public List<Extension> getList() {
		return list;
	}

	public void setList(List<Extension> list){
		this.list = list;
	}

	@Override
	public Iterator<Extension> iterator() {
		return list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Extensions [list=" + list + "]";
	}

}
