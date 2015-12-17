package com.infinities.nova.response.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "security_group")
public class SecurityGroupForCreate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String description;


	public SecurityGroupForCreate() {
		super();
	}

	public SecurityGroupForCreate(String name) {
		this.name = name;
	}

	public SecurityGroupForCreate(String name, String description) {
		this(name);
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SecurityGroupForCreate [name=" + name + ", description=" + description + "]";
	}

}
