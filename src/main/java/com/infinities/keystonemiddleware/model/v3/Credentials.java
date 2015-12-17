package com.infinities.keystonemiddleware.model.v3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Credentials implements Iterable<Credential>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "credentials")
	private List<Credential> list;


	/**
	 * @return the list
	 */
	public List<Credential> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Credentials [list=" + list + "]";
	}

	@Override
	public Iterator<Credential> iterator() {
		return list.iterator();
	}

}
