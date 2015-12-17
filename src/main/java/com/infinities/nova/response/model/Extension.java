package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "extension")
public class Extension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String alias;

	private String description;

	private String name;

	private String namespace;

	private Calendar updated;

	private List<Link> links = new ArrayList<Link>(0);


	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @return the updated
	 */
	public Calendar getUpdated() {
		return updated;
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Extension [alias=" + alias + ", description=" + description + ", name=" + name + ", namespace=" + namespace
				+ "]";
	}

}
