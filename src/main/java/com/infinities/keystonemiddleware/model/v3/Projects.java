package com.infinities.keystonemiddleware.model.v3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Projects implements Iterable<Project>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "projects")
	private List<Project> list;


	/**
	 * @return the list
	 */
	public List<Project> getList() {
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Projects [list=" + list + "]";
	}

	@Override
	public Iterator<Project> iterator() {
		return list.iterator();
	}

}
